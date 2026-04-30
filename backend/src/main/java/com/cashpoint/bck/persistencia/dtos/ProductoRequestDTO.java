package com.cashpoint.back.persistencia.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductoRequestDTO {
    private String nombre;
    private Double precio;
    private Integer stock;
    private Long categoriaId;
}
