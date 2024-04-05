package com.curso.java.unittests;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AritmeticaTest {
    
    private Aritmetica aritmetica;
    
    @BeforeEach
    public void init(){
        System.out.println("Iniciando antes de cada test");
    }
    
    
    
    @Test
    public void testSumar(){
        int resultado = this.aritmetica.sumar(5, 10);
        assertEquals(15, resultado);
    }
    
    
}
