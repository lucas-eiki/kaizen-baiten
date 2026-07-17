package io.github.lucas_eiki.kaizen_baiten_api.pedido.model;

import io.github.lucas_eiki.kaizen_baiten_api.common.model.Dinheiro;
import io.github.lucas_eiki.kaizen_baiten_api.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @CreationTimestamp
    @Column(nullable = false)
    private Instant criadoEm;

    private Instant deletadoEm;

    @Embedded
    @AttributeOverride(name = "valor", column = @Column(name = "valor_total", precision = 10, scale = 2, nullable = false))
    private Dinheiro valorTotal;

    @Embedded
    @AttributeOverride(name = "valor", column = @Column(name = "valor_desconto", precision = 10, scale = 2))
    private Dinheiro valorDesconto;

    // todo: implementar ManyToOne Evento
}
