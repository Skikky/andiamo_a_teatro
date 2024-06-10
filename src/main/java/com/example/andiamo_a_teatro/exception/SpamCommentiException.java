package com.example.andiamo_a_teatro.exception;

public class SpamCommentiException extends Exception {
    @Override
    public String getMessage() {
        return "Ao basta a spammà aspetta: ";
    }
}
