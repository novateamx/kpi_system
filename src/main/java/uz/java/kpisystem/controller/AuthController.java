package uz.java.kpisystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.java.kpisystem.dto.TokenResponse;
import uz.java.kpisystem.dto.UserInfo;
import uz.java.kpisystem.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestParam String username, @RequestParam String password) {
        return ResponseEntity.ok(service.login(username, password));
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserInfo> me() {
        return ResponseEntity.ok(service.me());
    }
}
