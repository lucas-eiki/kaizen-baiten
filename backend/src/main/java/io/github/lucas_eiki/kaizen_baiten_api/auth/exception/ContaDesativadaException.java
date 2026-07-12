package io.github.lucas_eiki.kaizen_baiten_api.auth.exception;

public class ContaDesativadaException extends RuntimeException {
    public ContaDesativadaException() {
        super("Conta desativada.");
    }
}
