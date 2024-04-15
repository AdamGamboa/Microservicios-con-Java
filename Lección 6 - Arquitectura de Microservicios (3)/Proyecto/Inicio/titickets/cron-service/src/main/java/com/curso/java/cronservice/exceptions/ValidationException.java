package com.curso.java.cronservice.exceptions;


public class ValidationException extends RuntimeException{
    
    public ValidationException(String message){
        super(message);
    }
}
