package uz.java.kpisystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.java.kpisystem.config.CustomUserDetails;
import uz.java.kpisystem.dto.TokenResponse;
import uz.java.kpisystem.dto.UserInfo;
import uz.java.kpisystem.entity.SessionUser;
import uz.java.kpisystem.entity.User;
import uz.java.kpisystem.entity.enums.SessionUserStatus;
import uz.java.kpisystem.repository.SessionUserRepository;
import uz.java.kpisystem.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final SessionUserRepository sessionUserRepository;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;

    public TokenResponse login(String username, String reqPassword) { // 1234
        User user = userRepository.findByUsername(username);
        if (!passwordEncoder.matches(reqPassword, user.getPassword())) { // gdsg53453453afsdfs@#@
            throw new RuntimeException("Invalid username or password");
        }

        SessionUser sessionUser = sessionUserRepository.findByUserId(user.getId());
        if (sessionUser != null) {
            String accessToken = jwtTokenService.generateToken(user.getId());
            String refreshToken = jwtTokenService.generateRefreshToken(user.getId());
            sessionUser.setAccessToken(accessToken);
            sessionUser.setRefreshToken(refreshToken);
            sessionUser.setStatus(SessionUserStatus.ACTIVE);
            sessionUserRepository.save(sessionUser);
            return TokenResponse.builder()
                    .accessToken(sessionUser.getAccessToken())
                    .refreshToken(sessionUser.getRefreshToken())
                    .build();
        }
        String accessToken = jwtTokenService.generateToken(user.getId());
        String refreshToken = jwtTokenService.generateRefreshToken(user.getId());
        sessionUserRepository.save(SessionUser.builder()
                .user(user)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .status(SessionUserStatus.ACTIVE)
                .build());
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public UserInfo me() {
        CustomUserDetails userDetails =
                (CustomUserDetails) org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(userDetails.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        return UserInfo.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .build();
    }
}
