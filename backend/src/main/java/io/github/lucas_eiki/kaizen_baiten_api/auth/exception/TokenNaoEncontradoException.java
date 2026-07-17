package io.github.lucas_eiki.kaizen_baiten_api.auth.exception;

public class TokenNaoEncontradoException extends RuntimeException {
    public TokenNaoEncontradoException(String message) {
        super(message);
    }
}
