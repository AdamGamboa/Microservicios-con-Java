
package com.curso.java.unittests;

import java.util.UUID;

public class RandomGenerator {
    
    public String getRandomString(){
        return UUID.randomUUID().toString();
    }
    
    public Integer getRandomNumber(){
        int min = 0; // Minimum value of range
        int max = 100; 
        int random_int = (int)Math.floor(Math.random() * (max - min + 1) + min);
        return random_int;
    }
}
