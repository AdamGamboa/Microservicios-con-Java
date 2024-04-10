package com.curso.java.bookservice.exceptions;

public class NotFoundException extends RuntimeException {
    
    public NotFoundException(String message){
        super(message);
    }
    
}
