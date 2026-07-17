package io.github.lucas_eiki.kaizen_baiten_api.usuario.dto;

public record UsuarioEdicaoRequest(
        String nome,
        String email,
        Long cargoId
) {
}
