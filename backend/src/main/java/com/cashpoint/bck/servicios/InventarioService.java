package com.cashpoint.bck.servicios;

import com.cashpoint.bck.persistencia.dtos.MovimientoInvRequestDTO;
import com.cashpoint.bck.persistencia.dtos.MovimientoInvResponseDTO;

import java.util.List;

public interface InventarioService {
    MovimientoInvResponseDTO registrarEntrada(MovimientoInvRequestDTO request);
    MovimientoInvResponseDTO registrarSalida(MovimientoInvRequestDTO request);

    @Transactional
    MovimientoResponseDTO registrarEntrada(MovimientoRequestDTO request);

    List<MovimientoInvResponseDTO> historialPorProducto(Long productoId);
    Integer stockActual(Long productoId);
}