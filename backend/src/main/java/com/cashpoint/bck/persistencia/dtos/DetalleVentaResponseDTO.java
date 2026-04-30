package com.cashpoint.back.persistencia.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DetalleVentaResponseDTO {
    private Long id;
    private Integer cantidad;
    private Double precioUnitario;
    private String productoNombre;
    private Double subtotal;
}
