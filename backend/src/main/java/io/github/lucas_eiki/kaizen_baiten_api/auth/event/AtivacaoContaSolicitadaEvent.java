package io.github.lucas_eiki.kaizen_baiten_api.auth.event;

public record AtivacaoContaSolicitadaEvent(
        String nome,
        String email,
        String token
) {
}
