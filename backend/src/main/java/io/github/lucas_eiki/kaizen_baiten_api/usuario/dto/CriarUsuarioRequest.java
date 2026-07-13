package io.github.lucas_eiki.kaizen_baiten_api.usuario.dto;

import io.github.lucas_eiki.kaizen_baiten_api.cargo.model.Cargo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarUsuarioRequest(
        @NotBlank
        String nome,

        @NotBlank
        @Email
        String email,

        @NotNull
        Cargo cargo
) {
}
