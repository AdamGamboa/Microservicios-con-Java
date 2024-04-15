
package com.curso.java.userservice.service;

import com.curso.java.userservice.entities.Usuario;
import com.curso.java.userservice.model.User;
import com.curso.java.userservice.model.UserStatus;
import com.curso.java.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    
   
    private User toUser(Usuario usuario){
        User user = new User();
        user.setId(usuario.getId());
        user.setAddress(usuario.getDireccion());
        user.setName(usuario.getNombre());
        user.setStatus(UserStatus.valueOf(usuario.getEstado()));
        user.setEmail(usuario.getEmail());
        return user;
    }
    
}
