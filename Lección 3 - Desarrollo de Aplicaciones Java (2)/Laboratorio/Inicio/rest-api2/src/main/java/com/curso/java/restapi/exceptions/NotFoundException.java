package com.curso.java.restapi.exceptions;

public class NotFoundException extends RuntimeException {
    
    public NotFoundException(String message){
        super(message);
    }
    
}
