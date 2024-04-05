package com.curso.java.maven.modulo1;

import com.curso.java.maven.modulo2.StringValidator;
import org.apache.commons.math3.util.FastMath;

public class MyApplication {
    
    public static void main(String [] args){
        new MyApplication().run();
    }
    
    public void run(){
        StringValidator validator = new StringValidator();
        if(!validator.estaVacio("Hello World")){
            String otroString = validator.reemplazar("Hello World", "World", "Mundo");
            System.out.println(otroString);
        } else {
            FastMath.abs(-42);
        }
    }
    
}
