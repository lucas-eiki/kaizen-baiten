package io.github.lucas_eiki.kaizen_baiten_api.auth.exception;

import io.github.lucas_eiki.kaizen_baiten_api.common.exception.BusinessException;
import io.github.lucas_eiki.kaizen_baiten_api.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class TokenInvalidoException extends BusinessException {
    public TokenInvalidoException() {
        super(
                HttpStatus.UNAUTHORIZED,
                ErrorCode.TOKEN_INVALIDO,
                "Token inválido ou expirado."
        );
    }
}
