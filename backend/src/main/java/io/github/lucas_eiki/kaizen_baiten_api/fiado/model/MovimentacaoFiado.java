package io.github.lucas_eiki.kaizen_baiten_api.fiado.model;

import io.github.lucas_eiki.kaizen_baiten_api.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movimentacao_fiado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoFiado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}
