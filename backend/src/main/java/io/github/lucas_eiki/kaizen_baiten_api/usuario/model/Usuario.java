package io.github.lucas_eiki.kaizen_baiten_api.usuario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 100, nullable = false)
    private String nome;

    @NotBlank
    @Email
    @Column(unique = true, nullable = false)
    private String email;

    private String senhaHash;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime criadoEm;

    private LocalDateTime deletadoEm;

    private LocalDateTime ativadoEm;

    private LocalDateTime primeiroAcessoEm;

    @NotNull
    @Column(nullable = false)
    private Cargo cargo;
}
