package com.cashpoint.bck.persistencia.repositorios;

import com.cashpoint.bck.persistencia.entidades.DetalleVentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleVentaRepository extends JpaRepository<DetalleVentaEntity, Long> {
}
