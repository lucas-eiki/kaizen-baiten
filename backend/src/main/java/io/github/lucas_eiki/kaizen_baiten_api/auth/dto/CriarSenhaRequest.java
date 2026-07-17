package io.github.lucas_eiki.kaizen_baiten_api.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CriarSenhaRequest(
        @NotBlank
        @Size(min = 8)
        String senha,

        @NotBlank
        String token
) {
}
