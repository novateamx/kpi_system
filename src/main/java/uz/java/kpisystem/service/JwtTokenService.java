package uz.java.kpisystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.java.kpisystem.util.JwtUtil;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final JwtUtil jwtUtil;

    @Value("${jwt.token.secret}")
    private String tokenSecret;

    public Boolean isValid(String token) {
        return jwtUtil.isTokenValid(token, getTokenSecret());
    }

    public String generateToken(Long subject) {
        return jwtUtil.jwt(new HashMap<>(), subject.toString(), getTokenSecret());
    }

    public String generateRefreshToken(Long subject) {
        return jwtUtil.refreshJwt(new HashMap<>(), subject.toString(), getTokenSecret());
    }

    public String subject(String token) {
        return jwtUtil.getSubject(token, getTokenSecret());
    }

    private String getTokenSecret() {
        return tokenSecret;
    }
}
