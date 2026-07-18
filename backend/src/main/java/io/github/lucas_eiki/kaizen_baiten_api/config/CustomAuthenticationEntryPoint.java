package io.github.lucas_eiki.kaizen_baiten_api.config;

import io.github.lucas_eiki.kaizen_baiten_api.common.dto.ErrorResponse;
import io.github.lucas_eiki.kaizen_baiten_api.common.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {

        ErrorCode codigo = ErrorCode.NAO_AUTENTICADO;
        String mensagem = "É necessário estar autenticado para acessar este recurso.";

        Throwable causa = authException.getCause();

        if (causa instanceof JwtValidationException jwtException) {

            boolean expirado = jwtException.getErrors()
                    .stream()
                    .anyMatch(error ->
                            error.getDescription() != null &&
                            error.getDescription().contains("expired")
                    );

            if (expirado) {
                codigo = ErrorCode.TOKEN_JWT_EXPIRADO;
                mensagem = "Sessão expirada. Faça login novamente.";
            }
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorResponse erro = new ErrorResponse(
                HttpStatus.UNAUTHORIZED,
                codigo,
                mensagem
        );

        objectMapper.writeValue(
                response.getOutputStream(),
                erro
        );
    }
}
