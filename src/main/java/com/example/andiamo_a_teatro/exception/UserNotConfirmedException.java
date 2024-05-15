package com.example.andiamo_a_teatro.exception;

public class UserNotConfirmedException extends Exception {
    @Override
    public String getMessage() {
        return "devi confermare l'account per poter accedere ";
    }
}
