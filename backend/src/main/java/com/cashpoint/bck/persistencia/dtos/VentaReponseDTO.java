package com.cashpoint.back.persistencia.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class VentaReponseDTO {
    private Long id;
    private LocalDateTime fecha;
    private Double total;
    private String estado;
}
