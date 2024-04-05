
package com.curso.java.unittests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MyServiceTest {
    
    private RandomGenerator generator;
    private MyService myService; 
    
    @BeforeEach
    public void init(){
        generator = mock(RandomGenerator.class);
        myService = new MyService(generator);
    }
    
    @Test 
    public void evenOrOdd_even(){
        when(generator.getRandomNumber())
                .thenReturn(2);
        
        String result = myService.evenOrOdd();
        assertEquals("El numero es par", result);
        
        verify(generator).getRandomNumber();
    }
    
    @Test 
    public void evenOrOdd_odd(){
        when(generator.getRandomNumber())
                .thenReturn(5);
        
        String result = myService.evenOrOdd();
        assertEquals("El numero es impar", result);
        
        verify(generator).getRandomNumber();
    }
    
    @Test
    public void convertirString_ok(){
        when(generator.getRandomString())
                .thenReturn("abcdef");
        String result = myService.convertirString("Hola mundo");
        assertEquals("HOLA MUNDO:ABCDEF", result);
        
        when(generator.getRandomString())
                .thenReturn("123zxy");
        String result2 = myService.convertirString("Java Rules");
        assertEquals("JAVA RULES:123ZXY", result2);
        
        verify(generator, times(2)).getRandomString();
        verify(generator, never()).getRandomNumber();
    }
    
}
