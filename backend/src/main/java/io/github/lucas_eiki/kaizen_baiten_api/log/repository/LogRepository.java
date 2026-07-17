package io.github.lucas_eiki.kaizen_baiten_api.log.repository;

import io.github.lucas_eiki.kaizen_baiten_api.log.model.Acao;
import io.github.lucas_eiki.kaizen_baiten_api.log.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface LogRepository extends JpaRepository<Log, Long> {
    boolean existsByUsuarioIdAndAcaoNotIn(Long id, Collection<Acao> acoes);

    void deleteAllByUsuarioId(Long id);
}
