package io.github.lucas_eiki.kaizen_baiten_api.usuario.exception;

import io.github.lucas_eiki.kaizen_baiten_api.common.exception.BusinessException;
import io.github.lucas_eiki.kaizen_baiten_api.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class UsuarioNaoEncontradoException extends BusinessException {
    public UsuarioNaoEncontradoException(Long id) {
        super(
                HttpStatus.NOT_FOUND,
                ErrorCode.USUARIO_NAO_ENCONTRADO,
                "Usuário com id " + id + " não encontrado."
        );
    }
}
