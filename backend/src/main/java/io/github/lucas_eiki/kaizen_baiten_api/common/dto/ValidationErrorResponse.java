package io.github.lucas_eiki.kaizen_baiten_api.common.dto;

import io.github.lucas_eiki.kaizen_baiten_api.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Map;

public record ValidationErrorResponse(
        int status,
        ErrorCode codigo,
        String mensagem,
        Instant dataHora,
        Map<String, String> erros
) {
    public ValidationErrorResponse(HttpStatus status, ErrorCode codigo, String mensagem, Map<String, String> erros) {
        this(status.value(), codigo, mensagem, Instant.now(), erros);
    }
}
