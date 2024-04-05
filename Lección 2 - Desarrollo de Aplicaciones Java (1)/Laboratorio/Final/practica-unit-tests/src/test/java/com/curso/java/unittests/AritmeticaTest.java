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
        aritmetica = new Aritmetica();
    }
    
    @AfterEach
    public void finish(){
        System.out.println("Finalizando un test");
    }
    
    @BeforeAll
    public static void setup(){
        System.out.println("Setup");
    }
    
    @Test
    public void testSumar(){
        int resultado = this.aritmetica.sumar(5, 10);
        assertEquals(15, resultado);
    }
    
    @Test
    public void testRestar(){
        int resultado = this.aritmetica.restar(20, 11);
        assertEquals(9, resultado);
    }
    
    @Test
    public void testMultiplicar(){
        long resultado = this.aritmetica.multiplicar(6, 12);
        assertEquals(72, resultado);
    }
    
    @Test
    public void testDividid(){
        double resultado = this.aritmetica.dividir(100, 20);
        assertEquals(5, resultado);
    }
    
    @Test
    public void testPotencia(){
        double resultado = this.aritmetica.potencia(2, 8);
        assertEquals(256, resultado);
    }
}
