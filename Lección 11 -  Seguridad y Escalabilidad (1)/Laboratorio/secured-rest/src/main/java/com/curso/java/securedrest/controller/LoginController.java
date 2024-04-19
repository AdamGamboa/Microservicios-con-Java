package com.curso.java.securedrest.controller;

import com.curso.java.securedrest.model.AuthRequest;
import com.curso.java.securedrest.model.JwtResponse;
import com.curso.java.securedrest.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {
    
    public final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    
    @Autowired
    public LoginController(AuthenticationManager authenticationManager, JwtService jwtService){
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }
   
    @PostMapping()
    public JwtResponse AuthenticateAndGetToken(@RequestBody AuthRequest authRequestDTO){
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.username(), authRequestDTO.password()));
    if(authentication.isAuthenticated()){
       return new JwtResponse(jwtService.generateToken(authRequestDTO.username()));
    } else {
        throw new UsernameNotFoundException("invalid user request..!!");
    }
} 
}
