package com.curso.java.securedrest.security;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Adam
 */
public class PlainTextPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
       return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        System.out.println("raw:"+rawPassword+" encoded:"+encodedPassword);
        return rawPassword.toString().equals(encodedPassword);
    }
    
}
