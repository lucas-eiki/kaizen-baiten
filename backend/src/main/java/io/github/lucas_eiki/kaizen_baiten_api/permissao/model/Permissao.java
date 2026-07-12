package io.github.lucas_eiki.kaizen_baiten_api.permissao.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "permissao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Permissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(255)", unique = true, nullable = false)
    private NomePermissao nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;
}
