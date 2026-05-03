package com.cashpoint.bck.servicios;

import com.cashpoint.bck.persistencia.dtos.CategoriaRequestDTO;
import com.cashpoint.bck.persistencia.dtos.CategoriaResponseDTO;

import java.util.List;

public interface CategoriaService {
    CategoriaResponseDTO crear(CategoriaRequestDTO request);
    List<CategoriaResponseDTO> listar();
    void eliminar(Long id);
}
