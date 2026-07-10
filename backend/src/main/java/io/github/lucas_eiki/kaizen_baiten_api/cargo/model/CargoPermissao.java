package io.github.lucas_eiki.kaizen_baiten_api.cargo.model;

import io.github.lucas_eiki.kaizen_baiten_api.permissao.model.Permissao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "cargo_permissao",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_cargo_permissao",
                        columnNames = {"cargo_id", "permissao_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CargoPermissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cargo_id", nullable = false)
    private Cargo cargo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "permissao_id", nullable = false)
    private Permissao permissao;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime criadoEm;
}
