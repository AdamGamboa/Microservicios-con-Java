package com.curso.java.userservice.controller;

import com.curso.java.userservice.model.User;
import com.curso.java.userservice.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
    
    @GetMapping
    public List<User> getUsers(){
        return null;
    }
    
    @GetMapping("/{userId}")
    public User findUser(@PathVariable long userId){
        return null;
    }
    

    
    @PostMapping("/limit-reset")
    public void resetUsersLimit(){
        
    }
    
}
