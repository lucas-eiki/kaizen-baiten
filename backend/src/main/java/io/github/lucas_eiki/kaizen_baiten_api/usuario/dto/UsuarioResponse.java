package io.github.lucas_eiki.kaizen_baiten_api.usuario.dto;

import io.github.lucas_eiki.kaizen_baiten_api.usuario.model.StatusUsuario;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        StatusUsuario status,
        String cargoNome,
        String imagemPerfilPath
) {
}
