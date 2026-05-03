package com.cashpoint.bck.persistencia.repositorios;

import com.cashpoint.bck.persistencia.entidades.MovimientoInvEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimientoInvRepository extends JpaRepository<MovimientoInvEntity, Long> {

    List<MovimientoInvEntity> findByProductoId(Long productoId);
}
