package com.example.andiamo_a_teatro.exception;

public class PoveroException extends Exception{
    @Override
    public String getMessage() {
        return "Saldo insufficiente per acquistare il biglietto";
    }
}
