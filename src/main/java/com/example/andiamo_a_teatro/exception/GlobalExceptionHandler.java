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

    @ExceptionHandler(PasswordDeboleException.class)
    public ResponseEntity<ErrorResponse> handlePasswordDeboleException(NoSpettacoliFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse("Password Debole Exception", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordUgualiException.class)
    public ResponseEntity<ErrorResponse> handlePasswordUgualiException(NoSpettacoliFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse("Password Uguali Exception", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordSbagliataException.class)
    public ResponseEntity<ErrorResponse> handlePasswordSbagliataException(NoSpettacoliFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse("Password Sbagliata Exception", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SpamCommentiException.class)
    public ResponseEntity<ErrorResponse> handleSpamCommentiException(NoSpettacoliFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse("Spam Commenti Exception", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LikeAssente.class)
    public ResponseEntity<ErrorResponse> handleLikeAssenteException(NoSpettacoliFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse("Like assente Exception", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LikePresente.class)
    public ResponseEntity<ErrorResponse> handleLikePresenteException(NoSpettacoliFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse("Like presente Exception", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
