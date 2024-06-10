package com.example.andiamo_a_teatro.exception;

public class PasswordDeboleException extends Exception{
    @Override
    public String getMessage() {
        return "La password deve essere di 8 caratteri, avere 1 maiuscola, 1 numero e un carattere speciale";
    }
}
