package io.github.lucas_eiki.kaizen_baiten_api.config;

import io.github.lucas_eiki.kaizen_baiten_api.common.dto.ErrorResponse;
import io.github.lucas_eiki.kaizen_baiten_api.common.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorResponse erro = new ErrorResponse(
                HttpStatus.FORBIDDEN,
                ErrorCode.ACESSO_NEGADO,
                "Você não possui permissão para acessar este recurso."
        );

        objectMapper.writeValue(
                response.getOutputStream(),
                erro
        );
    }
}
