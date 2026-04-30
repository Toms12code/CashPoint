package com.cashpoint.back.persistencia.repositorios;

import com.cashpoint.back.persistencia.entidades.DetalleVentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleVentaRepository extends JpaRepository<DetalleVentaEntity, Long> {
}
