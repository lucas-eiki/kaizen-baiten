package io.github.lucas_eiki.kaizen_baiten_api.usuario.controller;

import io.github.lucas_eiki.kaizen_baiten_api.usuario.dto.CriarUsuarioRequest;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.dto.UsuarioEdicaoRequest;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.dto.UsuarioEdicaoResponse;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.dto.UsuarioResponse;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<Page<UsuarioResponse>> listar(@PageableDefault(size = 20, sort = "nome") Pageable pageable) {
        Page<UsuarioResponse> response = usuarioService.listar(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioEdicaoResponse> buscar(@PathVariable Long id) {
        UsuarioEdicaoResponse response = usuarioService.buscar(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioEdicaoResponse> atualizar(@PathVariable Long id, @RequestBody UsuarioEdicaoRequest request) {
        UsuarioEdicaoResponse response = usuarioService.atualizar(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
