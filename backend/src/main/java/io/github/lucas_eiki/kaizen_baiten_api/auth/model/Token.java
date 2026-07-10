package io.github.lucas_eiki.kaizen_baiten_api.auth.model;

import io.github.lucas_eiki.kaizen_baiten_api.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "CHAR(64)", unique = true, nullable = false)
    private String tokenHash;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private TipoToken tipo;

    @Column(nullable = false)
    private LocalDateTime expiraEm;

    private LocalDateTime utilizadoEm;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}
