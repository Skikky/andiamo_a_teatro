package com.example.andiamo_a_teatro.exception;

public class NoSpettacoliFoundException extends Exception {
    @Override
    public String getMessage() {
        return "Nessuno spettacolo trovato con i criteri specificati";
    }
}
