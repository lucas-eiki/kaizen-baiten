package io.github.lucas_eiki.kaizen_baiten_api.usuario.exception;

import io.github.lucas_eiki.kaizen_baiten_api.common.exception.BusinessException;
import io.github.lucas_eiki.kaizen_baiten_api.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class EmailJaCadastradoException extends BusinessException {
    public EmailJaCadastradoException() {
        super(
                HttpStatus.CONFLICT,
                ErrorCode.EMAIL_JA_CADASTRADO,
                "O e-mail informado já está cadastrado."
        );
    }
}
