package com.cashpoint.bck.excepcione;

import com.cashpoint.bck.persistencia.dtos.ErrorDelResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalException extends RuntimeException{

    @ExceptionHandler(NoHayException.class)
    public ResponseEntity<ErrorDelResponseDTO> handlerNoHay(NoHayException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDelResponseDTO(404, ex.getMessage()));
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<ErrorDelResponseDTO> handlerNegocio(NegocioException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDelResponseDTO(400, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDelResponseDTO> handleGeneral(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDelResponseDTO(500, "error del server :/"));
    }
}
