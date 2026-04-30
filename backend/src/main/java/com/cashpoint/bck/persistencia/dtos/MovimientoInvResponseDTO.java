package com.cashpoint.back.persistencia.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MovimientoInvResponseDTO {
    private Long id;
    private Long productoId;
    private Integer cantidad;
    private String motivo;
    private String tipo;
    private LocalDateTime fecha;
}
