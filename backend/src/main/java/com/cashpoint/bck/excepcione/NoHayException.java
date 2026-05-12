package com.cashpoint.bck.excepcione;

public class NoHayException extends RuntimeException {
    public NoHayException(String message){
     super(message);
    }
}
