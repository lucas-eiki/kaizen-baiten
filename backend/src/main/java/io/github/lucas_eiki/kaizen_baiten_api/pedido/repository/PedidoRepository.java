package io.github.lucas_eiki.kaizen_baiten_api.pedido.repository;

import io.github.lucas_eiki.kaizen_baiten_api.pedido.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    boolean existsByUsuarioId(Long id);
}
