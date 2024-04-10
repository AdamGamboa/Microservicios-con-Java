package com.curso.java.stockservice.repository;

import com.curso.java.stockservice.model.Stock;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class StockRepository {
    
    
    private final Set<Stock> dataSource = new HashSet<>();
    private final AtomicLong sequence = new AtomicLong();
    
    
    public Stock save(Stock stock){
        if(stock.getId() == null){
            long newId = sequence.incrementAndGet();
            stock.setId(newId);
        }
        dataSource.add(stock);
        return stock;
    }
    
    public Optional<Stock> find(long stockId){
        return dataSource.stream().filter(stock -> stockId == stock.getId()).findFirst();
    }
        
}
