package io.github.lucas_eiki.kaizen_baiten_api.usuario.model;

import io.github.lucas_eiki.kaizen_baiten_api.cargo.model.Cargo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

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
    private LocalDateTime criadoEm;

    private LocalDateTime deletadoEm;

    private LocalDateTime ativadoEm;

    private LocalDateTime primeiroAcessoEm;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cargo_id", nullable = false)
    private Cargo cargo;

    private String imagemPerfilPath;
}
