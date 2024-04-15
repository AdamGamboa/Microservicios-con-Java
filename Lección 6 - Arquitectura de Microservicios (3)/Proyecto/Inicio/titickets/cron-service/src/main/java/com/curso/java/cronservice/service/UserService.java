
package com.curso.java.cronservice.service;


import com.curso.java.cronservice.client.userservice.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private final UserClient userClient;
    
    @Autowired
    public UserService(UserClient userClient){
        this.userClient = userClient;
    }
    
    public void resetUserLimits(){
        this.userClient.resetUserLimit();
    }
    
}
