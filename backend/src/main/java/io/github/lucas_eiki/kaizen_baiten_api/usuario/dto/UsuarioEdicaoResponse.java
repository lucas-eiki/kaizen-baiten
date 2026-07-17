package io.github.lucas_eiki.kaizen_baiten_api.usuario.dto;

public record UsuarioEdicaoResponse(
        Long id,
        String nome,
        String email,
        Long cargoId,
        String imagemPerfilPath
) {
}
