
package com.curso.java.stockservice.service;

import com.curso.java.stockservice.entities.EStock;
import com.curso.java.stockservice.exceptions.NotFoundException;
import com.curso.java.stockservice.exceptions.ValidationException;
import com.curso.java.stockservice.model.Stock;
import com.curso.java.stockservice.model.StockUpdate;
import com.curso.java.stockservice.repository.StockRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    
    private final StockRepository stockRepository;
    
    @Autowired
    public StockService(StockRepository stockRepository){
        this.stockRepository = stockRepository;
    }

    
    public Stock find(long stockId){
        Optional<EStock> stockOpt = this.stockRepository.findById(stockId);
        return stockOpt
                .map(this::toStock)
                .orElseThrow(() -> {
            return new NotFoundException(String.format("Stock "+stockId+" not found"));
        });
    }
    
    public Stock save(Stock stock){
        var eStock = this.toEStock(stock);
        var savedEStock = this.stockRepository.save(eStock);
        return this.toStock(savedEStock);
    }
    
    public Stock update(long stockId, StockUpdate stockUpdate){
        var stock = this.find(stockId);
        if(stock.getCurrentQuantity() + stockUpdate.getIncreaseQuantity() < 0){
            throw new ValidationException(String.format("Not enough quantity in stock "+stockId));
        } else {
            stock.setCurrentQuantity(stock.getCurrentQuantity()+stockUpdate.getIncreaseQuantity());
        }
        var eStock = this.toEStock(stock);
        this.stockRepository.save(eStock);
        return stock;
    }
    
    
    private Stock toStock(EStock eStock){
        Stock stock = new Stock();
        stock.setId(eStock.getId());
        stock.setCurrentQuantity(eStock.getCurrentQuantity());
        return stock;
    }
    
    private EStock toEStock(Stock stock){
        EStock eStock = new EStock();
        eStock.setId(stock.getId());
        eStock.setCurrentQuantity(stock.getCurrentQuantity());
        return eStock;
    }
}
