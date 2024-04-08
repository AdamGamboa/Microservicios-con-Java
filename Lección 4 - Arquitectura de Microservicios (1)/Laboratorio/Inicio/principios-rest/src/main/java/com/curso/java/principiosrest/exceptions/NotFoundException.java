package com.curso.java.principiosrest.exceptions;

public class NotFoundException extends RuntimeException {
    
    public NotFoundException(String message){
        super(message);
    }
    
}
