package io.github.lucas_eiki.kaizen_baiten_api.common.exception;

import org.springframework.http.HttpStatus;

public class OperacaoNaoPermitidaException extends BusinessException {
    public OperacaoNaoPermitidaException(String message) {
        super(
                HttpStatus.CONFLICT,
                ErrorCode.OPERACAO_NAO_PERMITIDA,
                message
        );
    }
}
