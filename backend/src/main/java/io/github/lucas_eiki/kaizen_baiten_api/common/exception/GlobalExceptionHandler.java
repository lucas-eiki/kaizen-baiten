package io.github.lucas_eiki.kaizen_baiten_api.common.exception;

import io.github.lucas_eiki.kaizen_baiten_api.common.dto.ErrorResponse;
import io.github.lucas_eiki.kaizen_baiten_api.common.dto.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private ResponseEntity<ErrorResponse> buildError(HttpStatus status, ErrorCode codigo, String mensagem) {
        return ResponseEntity.status(status).body(new ErrorResponse(status, codigo, mensagem));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> erros = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(erro ->
                        erros.put(erro.getField(), erro.getDefaultMessage()));

        return ResponseEntity.badRequest()
                .body(new ValidationErrorResponse(
                        HttpStatus.BAD_REQUEST,
                        ErrorCode.CAMPOS_INVALIDOS,
                        "Campos inválidos.",
                        erros
                ));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        return buildError(ex.getStatus(), ex.getErrorCode(), ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        return buildError(HttpStatus.UNAUTHORIZED, ErrorCode.CREDENCIAIS_INVALIDAS, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.ERRO_INTERNO, "Ocorreu um erro interno no servidor.");
    }
}
