package io.github.lucas_eiki.kaizen_baiten_api.auth.exception;

public class ContaNaoAtivadaException extends RuntimeException {
    public ContaNaoAtivadaException() {
        super("Conta não ativada.");
    }
}
