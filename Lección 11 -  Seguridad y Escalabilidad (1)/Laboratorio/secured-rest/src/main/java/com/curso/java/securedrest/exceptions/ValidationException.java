package com.curso.java.securedrest.exceptions;


public class ValidationException extends RuntimeException{
    
    public ValidationException(String message){
        super(message);
    }
}
