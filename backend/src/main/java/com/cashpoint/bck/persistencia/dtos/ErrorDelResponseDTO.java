package com.cashpoint.bck.persistencia.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ErrorDelResponseDTO {
    public int status;
    public String message;
    public LocalDateTime hora;

    public ErrorDelResponseDTO (int status, String message) {
        this.status = status;
        this.message = message;
        this.hora = hora;
    }
}

