package io.github.lucas_eiki.kaizen_baiten_api.usuario.model;

import io.github.lucas_eiki.kaizen_baiten_api.cargo.model.Cargo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    private String senhaHash;

    @CreationTimestamp
    @Column(nullable = false)
    private Instant criadoEm;

    private Instant deletadoEm;

    private Instant ativadoEm;

    private Instant primeiroAcessoEm;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cargo_id", nullable = false)
    private Cargo cargo;

    private String imagemPerfilPath;
}
