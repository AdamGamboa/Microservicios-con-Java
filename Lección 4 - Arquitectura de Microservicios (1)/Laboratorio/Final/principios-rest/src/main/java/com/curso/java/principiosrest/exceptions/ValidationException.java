package com.curso.java.principiosrest.exceptions;


public class ValidationException extends RuntimeException{
    
    public ValidationException(String message){
        super(message);
    }
}
