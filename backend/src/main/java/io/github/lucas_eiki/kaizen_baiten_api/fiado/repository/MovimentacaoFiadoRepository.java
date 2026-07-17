package io.github.lucas_eiki.kaizen_baiten_api.fiado.repository;

import io.github.lucas_eiki.kaizen_baiten_api.fiado.model.MovimentacaoFiado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoFiadoRepository extends JpaRepository<MovimentacaoFiado, Long> {
    boolean existsByUsuarioId(Long id);
}
