package io.github.lucas_eiki.kaizen_baiten_api.common.dto;

import io.github.lucas_eiki.kaizen_baiten_api.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import java.time.Instant;

public record ErrorResponse(
        int status,
        ErrorCode codigo,
        String mensagem,
        Instant dataHora
) {
    public ErrorResponse(HttpStatus status, ErrorCode codigo, String mensagem) {
        this(status.value(), codigo, mensagem, Instant.now());
    }
}
