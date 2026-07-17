package io.github.lucas_eiki.kaizen_baiten_api.cargo.exception;

public class CargoNaoEncontradoException extends RuntimeException {
    public CargoNaoEncontradoException(Long id) {
        super("Cargo com id " + id + " não encontrado.");
    }
}
