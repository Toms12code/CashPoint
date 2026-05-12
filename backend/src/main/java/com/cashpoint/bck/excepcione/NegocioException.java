package com.cashpoint.bck.excepcione;

public class NegocioException extends RuntimeException{
    public NegocioException(String message){
        super(message);
    }
}
