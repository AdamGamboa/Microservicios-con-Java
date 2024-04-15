package com.curso.java.orderservice.repository;

import com.curso.java.orderservice.entities.Orden;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends CrudRepository<Orden, Long>{
    
    
}
