package com.curso.java.maven.modulo2;

import org.apache.commons.lang3.StringUtils;

public class StringValidator {
    
    public boolean estaVacio(String value){
        return StringUtils.isBlank(value);
    }
    
    public String reemplazar(String texto, String placeholder, String nuevoValor){
        return StringUtils.replace(texto, placeholder, nuevoValor);
    }
    
}
