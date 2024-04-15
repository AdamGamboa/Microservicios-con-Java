package com.curso.java.userservice.exceptions;


public class ValidationException extends RuntimeException{
    
    public ValidationException(String message){
        super(message);
    }
}
