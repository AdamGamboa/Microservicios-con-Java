package com.curso.java.bookservice.repository;

import com.curso.java.bookservice.client.entities.EBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<EBook, Long>{
    
    
    
}
