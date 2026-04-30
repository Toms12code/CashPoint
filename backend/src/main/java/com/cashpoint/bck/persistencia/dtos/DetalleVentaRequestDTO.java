package com.cashpoint.back.persistencia.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DetalleVentaRequestDTO {
    private Long productoId;
    private Integer cantidad;
}
