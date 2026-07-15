package io.github.lucas_eiki.kaizen_baiten_api.cargo.repository;

import io.github.lucas_eiki.kaizen_baiten_api.cargo.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
}
