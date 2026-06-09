package com.cashpoint.bck.servicios;

import com.cashpoint.bck.persistencia.dtos.ProductoRequestDTO;
import com.cashpoint.bck.persistencia.dtos.ProductoResponseDTO;


import java.util.List;



public interface ProductoService {
    ProductoResponseDTO crear(ProductoRequestDTO request);
    List<ProductoResponseDTO> listar();
    ProductoResponseDTO obtenerPorId(Long id);
    ProductoResponseDTO actualizar(Long id, ProductoRequestDTO request);
    void eliminar(Long id);
}
