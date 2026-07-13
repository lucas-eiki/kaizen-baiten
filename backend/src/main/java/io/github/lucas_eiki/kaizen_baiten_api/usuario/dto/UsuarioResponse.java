package io.github.lucas_eiki.kaizen_baiten_api.usuario.dto;

import io.github.lucas_eiki.kaizen_baiten_api.cargo.model.Cargo;

import java.time.LocalDateTime;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        LocalDateTime deletado_em,
        LocalDateTime ativado_em,
        Cargo cargo
) {
}
