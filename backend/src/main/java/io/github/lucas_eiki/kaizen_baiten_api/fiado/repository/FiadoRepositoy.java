package io.github.lucas_eiki.kaizen_baiten_api.fiado.repository;

import io.github.lucas_eiki.kaizen_baiten_api.fiado.model.Fiado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FiadoRepositoy extends JpaRepository<Fiado, Long> {
    boolean existsByUsuarioId(Long id);
}
