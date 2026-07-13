package io.github.lucas_eiki.kaizen_baiten_api.usuario.controller;

import io.github.lucas_eiki.kaizen_baiten_api.usuario.dto.CriarUsuarioRequest;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.dto.UsuarioResponse;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponse> criarUsuario(@RequestBody @Valid CriarUsuarioRequest request) {
        UsuarioResponse response = usuarioService.criarUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
