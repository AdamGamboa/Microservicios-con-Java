package com.curso.java.bookservice.service;

import com.curso.java.bookservice.client.stockservice.Stock;
import com.curso.java.bookservice.client.stockservice.StockClient;
import com.curso.java.bookservice.client.stockservice.StockUpdate;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    
    private final StockClient stockClient;
    
    private final Map<Long, Stock> cache;
    
    @Autowired
    public StockService(StockClient stockClient){
        this.stockClient = stockClient;
        this.cache = new HashMap<>();
    }
    
    
    public Stock find(Long stockId){
        return null;
    }
    
    public Stock save(Stock stock){
        return null;
    }
    
    public Stock update(StockUpdate stock, long stockId){
        return null;
    }
    
}
