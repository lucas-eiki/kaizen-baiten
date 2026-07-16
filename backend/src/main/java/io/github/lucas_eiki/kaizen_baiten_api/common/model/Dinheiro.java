package io.github.lucas_eiki.kaizen_baiten_api.common.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Embeddable
public record Dinheiro(
        @JsonValue
        BigDecimal valor
) {
    public Dinheiro(BigDecimal valor) {
        if (valor == null) {
            throw new IllegalArgumentException("O valor não pode ser nulo");
        }
        this.valor = valor.setScale(2, RoundingMode.HALF_EVEN);
    }

    @JsonCreator
    public static Dinheiro de(BigDecimal valor) {
        return new Dinheiro(valor);
    }

    public Dinheiro somar(Dinheiro outro) {
        return new Dinheiro(this.valor.add(outro.valor));
    }

    public Dinheiro subtrair(Dinheiro outro) {
        BigDecimal resultado = this.valor.subtract(outro.valor);
        if (resultado.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O saldo não pode ficar negativo");
        }
        return new Dinheiro(resultado);
    }
}
