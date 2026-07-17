package io.github.lucas_eiki.kaizen_baiten_api.log.model;

import io.github.lucas_eiki.kaizen_baiten_api.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "log")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(45)", nullable = false)
    private Acao acao;

    // todo: implementar resto da entidade

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
