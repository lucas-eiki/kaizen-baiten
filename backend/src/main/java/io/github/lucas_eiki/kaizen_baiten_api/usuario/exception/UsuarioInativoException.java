package io.github.lucas_eiki.kaizen_baiten_api.usuario.exception;

import io.github.lucas_eiki.kaizen_baiten_api.common.exception.BusinessException;
import io.github.lucas_eiki.kaizen_baiten_api.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class UsuarioInativoException extends BusinessException {
    public UsuarioInativoException(Long id) {
        super(
                HttpStatus.CONFLICT,
                ErrorCode.USUARIO_INATIVO,
                "Usuário com id " + id + " está inativo."
        );
    }
}
