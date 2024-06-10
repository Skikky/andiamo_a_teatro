package com.example.andiamo_a_teatro.exception;

public class PasswordUgualiException extends Exception{
    @Override
    public String getMessage() {
        return "La nuova password non può essere uguale a quella vecchia ";
    }
}
