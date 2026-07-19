package io.github.lucas_eiki.kaizen_baiten_api.auth.exception;

import io.github.lucas_eiki.kaizen_baiten_api.common.exception.BusinessException;
import io.github.lucas_eiki.kaizen_baiten_api.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class TokenNaoEncontradoException extends BusinessException {
    public TokenNaoEncontradoException() {
        super(
                HttpStatus.UNAUTHORIZED,
                ErrorCode.TOKEN_NAO_ENCONTRADO,
                "Token não encontrado"
        );
    }
}
