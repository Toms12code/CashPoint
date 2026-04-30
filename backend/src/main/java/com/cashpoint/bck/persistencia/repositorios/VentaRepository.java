package com.cashpoint.back.persistencia.repositorios;

import com.cashpoint.back.persistencia.entidades.VentaEntity;
import com.cashpoint.back.persistencia.entidades.enums.EstadoVenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VentaRepository extends JpaRepository<VentaEntity, Long> {

    List<VentaEntity> findByEstado(EstadoVenta estado);
}
