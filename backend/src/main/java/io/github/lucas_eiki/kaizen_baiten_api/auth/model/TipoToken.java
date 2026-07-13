package io.github.lucas_eiki.kaizen_baiten_api.auth.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

@Getter
@RequiredArgsConstructor
public enum TipoToken {
    ATIVACAO_CONTA(
            Duration.ofHours(48),
            "48 horas"
    ),
    RECUPERACAO_SENHA(
            Duration.ofMinutes(30),
            "30 minutos"
    ),
    TROCA_SENHA(
            Duration.ofMinutes(30),
            "30 minutos"
    ),
    TROCA_EMAIL(
            Duration.ofMinutes(30),
            "30 minutos"
    );

    private final Duration validade;
    private final String descricaoValidade;
}
