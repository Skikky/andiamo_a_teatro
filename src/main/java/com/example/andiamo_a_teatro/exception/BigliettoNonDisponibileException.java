package com.example.andiamo_a_teatro.exception;

public class BigliettoNonDisponibileException extends Exception{
    @Override
    public String getMessage() {
        return "ao t'hanno fregato il biglietto";
    }
}
