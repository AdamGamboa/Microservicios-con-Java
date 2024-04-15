
package com.curso.java.orderservice.controller.exceptions;

import com.curso.java.orderservice.exceptions.NotFoundException;
import com.curso.java.orderservice.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionController {
    
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException ex) {
        String bodyOfResponse = ex.getMessage();
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<String> handleValidation(ValidationException ex) {
        String bodyOfResponse = ex.getMessage();
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.BAD_REQUEST);
    }
}
