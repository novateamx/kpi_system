package uz.java.kpisystem.service;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
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
import uz.java.kpisystem.exception.CustomNotFoundException;
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

    @Transactional
    public Long update(Long id, UserRequest request) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new GenericRuntimeException("User not found")
        );
        userMapper.updateFromRequest(request, user);

        if (request.getRoleId() != null) {
            Role role = roleRepository.findById(request.getRoleId()).orElseThrow(
                    () -> new GenericRuntimeException("Role not found")
            );
            user.setRole(role);
        }

        if (request.getOrganizationId() != null) {
            Organization organization = organizationRepository.findById(request.getOrganizationId())
                    .orElseThrow(() -> new GenericRuntimeException("Organization not found"));
            user.setOrganization(Set.of(organization));
        }

        UserRepresentation oldRep = null;
        try {
            UserResource userResource = keycloak.realm(realm).users()
                    .get(user.getKeycloakId().toString());

            oldRep = userResource.toRepresentation();

            UserRepresentation updated = userResource.toRepresentation();
            if (request.getFirstName() != null) updated.setFirstName(request.getFirstName());
            if (request.getLastName() != null) updated.setLastName(request.getLastName());
            if (request.getEmail() != null) updated.setEmail(request.getEmail());

            userResource.update(updated);

        } catch (Exception e) {
            log.error("Keycloak update failed for user id={}: {}", id, e.getMessage(), e);
            throw new GenericRuntimeException("Keycloak update failed, transaction rolled back");
        }

        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.error("DB save failed for user id={}, rolling back Keycloak: {}", id, e.getMessage(), e);
            rollbackKeycloakUser(user.getKeycloakId().toString(), oldRep);
            throw new GenericRuntimeException("DB save failed, Keycloak rolled back");
        }

        return user.getId();
    }

    private void rollbackKeycloakUser(String keycloakId, UserRepresentation oldRep) {
        if (oldRep == null) return;
        try {
            keycloak.realm(realm).users().get(keycloakId).update(oldRep);
            log.info("Keycloak rollback successful for keycloakId={}", keycloakId);
        } catch (Exception ex) {
            log.error("CRITICAL: Keycloak rollback FAILED for keycloakId={}. Manual fix required! Snapshot: {}",
                    keycloakId, oldRep, ex);
        }
    }

    @Transactional
    public Boolean delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new CustomNotFoundException("User not found")
        );
        UserResource oldUser = keycloak.realm(realm).users().get(user.getKeycloakId().toString());
        try {
            keycloak.realm(realm).users().delete(user.getKeycloakId().toString());
        } catch (Exception e) {
            throw new GenericRuntimeException("Keycloak da user ochirishda xatolik");
        }
        try {
            userRepository.delete(user);
        } catch (Exception e) {
            keycloak.realm(realm).users().create(oldUser.toRepresentation());
        }
        return true;
    }
}
