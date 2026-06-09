package com.cashpoint.bck.servicios;

import com.cashpoint.bck.persistencia.dtos.VentaRequestDTO;
import com.cashpoint.bck.persistencia.dtos.VentaResponseDTO;
import jakarta.transaction.Transactional;


import java.util.List;

public interface VentaService {
    VentaResponseDTO crear(VentaRequestDTO request);
    List<VentaResponseDTO> listar();
    VentaResponseDTO obtenerPorId(Long id);
    void eliminar(Long id);

    @Transactional
    VentaResponseDTO anular(Long id);
}
