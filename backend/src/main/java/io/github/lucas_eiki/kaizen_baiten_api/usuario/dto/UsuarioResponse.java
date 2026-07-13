package io.github.lucas_eiki.kaizen_baiten_api.usuario.dto;

import io.github.lucas_eiki.kaizen_baiten_api.cargo.model.Cargo;

import java.time.Instant;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        Instant deletado_em,
        Instant ativado_em,
        Cargo cargo
) {
}
