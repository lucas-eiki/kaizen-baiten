package io.github.lucas_eiki.kaizen_baiten_api.cargo.exception;

import io.github.lucas_eiki.kaizen_baiten_api.common.exception.BusinessException;
import io.github.lucas_eiki.kaizen_baiten_api.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class CargoNaoEncontradoException extends BusinessException {
    public CargoNaoEncontradoException(Long id) {
        super(
                HttpStatus.NOT_FOUND,
                ErrorCode.CARGO_NAO_ENCONTRADO,
                "Cargo com id " + id + " não encontrado."
        );
    }
}
