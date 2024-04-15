package com.curso.java.orderservice.exceptions;


public class ValidationException extends RuntimeException{
    
    public ValidationException(String message){
        super(message);
    }
}
