package io.github.lucas_eiki.kaizen_baiten_api.auth.exception;

import io.github.lucas_eiki.kaizen_baiten_api.common.exception.BusinessException;
import io.github.lucas_eiki.kaizen_baiten_api.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class ContaJaAtivadaException extends BusinessException {
    public ContaJaAtivadaException(Long id) {
        super(
                HttpStatus.CONFLICT,
                ErrorCode.CONTA_JA_ATIVADA,
                "Usuário com id " + id + " já está ativado"
        );
    }
}
