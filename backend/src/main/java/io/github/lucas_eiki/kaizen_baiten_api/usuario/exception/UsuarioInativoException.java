package io.github.lucas_eiki.kaizen_baiten_api.usuario.exception;

public class UsuarioInativoException extends RuntimeException {
    public UsuarioInativoException(Long id) {
        super("Usuário com id " + id + " está inativo.");
    }
}
