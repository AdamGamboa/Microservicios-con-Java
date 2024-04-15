package com.curso.java.stockservice.controller;

import com.curso.java.stockservice.model.Stock;
import com.curso.java.stockservice.model.StockUpdate;
import com.curso.java.stockservice.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stocks")
public class StockController {
    
    private final StockService stockService;
    
    @Autowired
    public StockController(StockService stockService){
        this.stockService = stockService;
    }
        
    @GetMapping("/{stockId}")
    public Stock findStock(@PathVariable long stockId){
        return stockService.find(stockId);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Stock save(@RequestBody Stock stock){
        return this.stockService.save(stock);
    }
    
    @PutMapping("/{stockId}")
    public Stock update(@RequestBody StockUpdate stock, @PathVariable long stockId){
        return this.stockService.update(stockId, stock);
    }
    
}
