package io.github.lucas_eiki.kaizen_baiten_api.auth.controller;

import io.github.lucas_eiki.kaizen_baiten_api.auth.dto.LoginRequest;
import io.github.lucas_eiki.kaizen_baiten_api.auth.dto.LoginResponse;
import io.github.lucas_eiki.kaizen_baiten_api.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
