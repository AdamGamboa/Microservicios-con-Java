package com.curso.java.stockservice.repository;

import com.curso.java.stockservice.entities.EStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<EStock, Long>{
   
}
