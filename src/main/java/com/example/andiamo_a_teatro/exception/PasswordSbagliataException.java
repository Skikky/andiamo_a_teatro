package com.example.andiamo_a_teatro.exception;

public class PasswordSbagliataException extends Exception{
    @Override
    public String getMessage() {
        return "Hai sbagliato ad inserire la password";
    }
}
