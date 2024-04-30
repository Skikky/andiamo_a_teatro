package com.example.andiamo_a_teatro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorResponse errorResponse = new ErrorResponse("Illegal Argument Exception", "Id non presente nella tabella");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse("Entity not found Exception", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PoveroException.class)
    public ResponseEntity<ErrorResponse> handlePoveroException(PoveroException e) {
        ErrorResponse errorResponse = new ErrorResponse("Povero Exception", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SpettacoloNonVistoException.class)
    public ResponseEntity<ErrorResponse> handleSpettacoloNonVistoException(SpettacoloNonVistoException e) {
        ErrorResponse errorResponse = new ErrorResponse("Spettacolo Non Visto Exception", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoSpettacoliFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoSpettacoliFoundException(NoSpettacoliFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse("No Spettacoli Found Exception", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
