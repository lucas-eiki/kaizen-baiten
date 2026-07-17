package io.github.lucas_eiki.kaizen_baiten_api.usuario.exception;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(Long id) {
        super("Usuário com id " + id + " não encontrado.");
    }
}
