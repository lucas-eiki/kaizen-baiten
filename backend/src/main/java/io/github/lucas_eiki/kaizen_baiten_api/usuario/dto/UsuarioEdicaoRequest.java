package io.github.lucas_eiki.kaizen_baiten_api.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UsuarioEdicaoRequest(
        @Size(min = 3, message = "Nome deve ter no mínimo 3 caracteres")
        String nome,

        @Email(message = "E-mail deve ser válido")
        String email,

        Long cargoId
) {
}
