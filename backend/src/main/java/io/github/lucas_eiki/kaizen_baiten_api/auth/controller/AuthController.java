package io.github.lucas_eiki.kaizen_baiten_api.auth.controller;

import io.github.lucas_eiki.kaizen_baiten_api.auth.dto.CriarSenhaRequest;
import io.github.lucas_eiki.kaizen_baiten_api.auth.dto.LoginRequest;
import io.github.lucas_eiki.kaizen_baiten_api.auth.dto.LoginResponse;
import io.github.lucas_eiki.kaizen_baiten_api.auth.dto.ValidacaoTokenResponse;
import io.github.lucas_eiki.kaizen_baiten_api.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/ativacao")
    public ResponseEntity<ValidacaoTokenResponse> validarToken(@RequestParam String token) {
        ValidacaoTokenResponse response = authService.validarToken(token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/ativacao")
    public ResponseEntity<Void> ativarConta(@RequestBody @Valid CriarSenhaRequest request) {
        authService.ativarConta(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
