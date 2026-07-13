package io.github.lucas_eiki.kaizen_baiten_api.auth.repository;

import io.github.lucas_eiki.kaizen_baiten_api.auth.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
}
