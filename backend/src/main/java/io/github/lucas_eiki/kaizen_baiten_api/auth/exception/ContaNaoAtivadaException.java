package io.github.lucas_eiki.kaizen_baiten_api.auth.exception;

import io.github.lucas_eiki.kaizen_baiten_api.common.exception.BusinessException;
import io.github.lucas_eiki.kaizen_baiten_api.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class ContaNaoAtivadaException extends BusinessException {
    public ContaNaoAtivadaException() {
        super(
                HttpStatus.CONFLICT,
                ErrorCode.CONTA_NAO_ATIVADA,
                "Conta não ativada."
        );
    }
}
