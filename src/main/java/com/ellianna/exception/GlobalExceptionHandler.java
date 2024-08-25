package com.ellianna.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<?> handleBadRequest(BadRequest badRequest){
        return new ResponseEntity<>(badRequest.getError(), HttpStatus.BAD_REQUEST);
    }
}
