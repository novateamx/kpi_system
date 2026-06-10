package uz.java.kpisystem.service;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.java.kpisystem.dto.UserRequest;
import uz.java.kpisystem.entity.Organization;
import uz.java.kpisystem.entity.Role;
import uz.java.kpisystem.entity.User;
import uz.java.kpisystem.exception.GenericRuntimeException;
import uz.java.kpisystem.mapper.UserMapper;
import uz.java.kpisystem.repository.OrganizationRepository;
import uz.java.kpisystem.repository.RoleRepository;
import uz.java.kpisystem.repository.UserRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.Family.SUCCESSFUL;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final Keycloak keycloak;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final OrganizationRepository organizationRepository;
    private final RoleRepository roleRepository;

    @Value("${app.keycloak.realm}")
    private String realm;

    @Transactional
    public Long create(UserRequest request) {
        if (!checkUsername(request.getUsername())) {
            throw new GenericRuntimeException(request.getUsername());
        }
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(request.getFirstName());
        userRepresentation.setEnabled(true);
        userRepresentation.setLastName(request.getLastName());
        userRepresentation.setEmail(request.getEmail());
        userRepresentation.setEmailVerified(true);
        userRepresentation.setUsername(request.getUsername());
        var password = new CredentialRepresentation();
        password.setType(CredentialRepresentation.PASSWORD);
        password.setTemporary(false);
        password.setValue(request.getPassword());
        userRepresentation.setCredentials(List.of(password));
        String keycloakUserId = null;
        try (Response response = keycloak.realm(realm)
                .users()
                .create(userRepresentation)) {

            if (response.getStatusInfo().getFamily() != SUCCESSFUL) {
                throw new GenericRuntimeException("keycloak.user.create.failed");
            }

            keycloakUserId = extractUserId(response);
            return saveEntity(request, keycloakUserId);

        } catch (Exception e) {
            // rollback Keycloak user (faqat yaratilgan bo‘lsa)
            if (keycloakUserId != null) {
                try {
                    keycloak.realm(realm)
                            .users()
                            .delete(keycloakUserId);
                } catch (Exception ex) {
                    log.error("Failed to rollback Keycloak user: {}", keycloakUserId, ex);
                }
            }
            throw new GenericRuntimeException(e.getMessage());
        }
    }

    private String extractUserId(Response response) {
        String location = response.getLocation().toString();
        return location.substring(location.lastIndexOf("/") + 1);
    }

    public Long saveEntity(UserRequest request, String keycloakUserId) {
        User entity = userMapper.toEntity(request);

        if (request.getRoleId() != null) {
            Role role = roleRepository.findById(request.getRoleId()).orElseThrow(() ->
                    new GenericRuntimeException("Role not found"));
            entity.setRole(role);
        }
        if (request.getOrganizationId() != null) {
            Organization organization = organizationRepository.findById(request.getOrganizationId())
                    .orElseThrow(() -> new GenericRuntimeException("organizationId not found"));
            entity.setOrganization(Set.of(organization));
        }
        entity.setKeycloakId(UUID.fromString(keycloakUserId));
        entity.setPassword(passwordEncoder.encode(request.getPassword()));
        User save = userRepository.save(entity);
//        cacheManagerService.delete(CachePrefix.USER);
        return save.getId();
    }

    private boolean checkUsername(String username) {
        return keycloak.realm(realm)
                .users()
                .searchByUsername(username, false).isEmpty();
    }
}
