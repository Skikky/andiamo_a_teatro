package com.example.andiamo_a_teatro.exception;

public class SpettacoloNonVistoException extends  Exception{
    @Override
    public String getMessage(){
        return "Non hai visto lo spettacolo non puoi fare il critico";
    }
}
