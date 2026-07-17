package io.github.lucas_eiki.kaizen_baiten_api.auth.exception;

public class ContaJaAtivadaException extends RuntimeException {
    public ContaJaAtivadaException(Long id) {
        super("Usuário com id " + id + " já está ativado");
    }
}
