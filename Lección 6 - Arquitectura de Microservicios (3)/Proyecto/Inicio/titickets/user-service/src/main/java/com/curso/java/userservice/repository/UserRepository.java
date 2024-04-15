package com.curso.java.userservice.repository;

import com.curso.java.userservice.entities.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Usuario, Long> {
    
    
    @Modifying
    @Query("update Usuario u set u.tiquetesComprados = 0")
    void resetUsersLimit();
    
}
