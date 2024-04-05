package com.curso.java.unittests;

public class MyService {
    
    
    private RandomGenerator generator;
    
    public MyService(RandomGenerator generator){
        this.generator = generator;
    }
    
    public String evenOrOdd(){
        int randomNumber = generator.getRandomNumber();
        if(randomNumber % 2 == 0){
            return "El numero es par";
        }
        else {
            return "El numero es impar";
        }
    }
    
    /**
     * Recibe un string abc y lo convierte en otro string 
     * ABC:RANDOM_STRING
     * 
     * @param texto
     * @return 
     */
    public String convertirString(String texto){
        return (texto+":"+generator.getRandomString()).toUpperCase();
    }
}
