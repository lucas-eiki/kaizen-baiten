package io.github.lucas_eiki.kaizen_baiten_api.auth.repository;

import io.github.lucas_eiki.kaizen_baiten_api.auth.model.TipoToken;
import io.github.lucas_eiki.kaizen_baiten_api.auth.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findByUsuarioIdAndTipoAndUtilizadoEmIsNullAndExpiraEmAfter(
            Long usuarioId,
            TipoToken tipo,
            Instant agora
    );

    void deleteAllByUsuarioId(Long id);

    Optional<Token> findByTokenHash(String tokenHash);
}
