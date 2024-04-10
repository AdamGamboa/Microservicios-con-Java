
package com.curso.java.bookservice.client.stockservice;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "stock-service", url = "http://localhost:8091", path = "/api/stocks")
public interface StockClient {
    
    @GetMapping("/{stockId}")
    ResponseEntity<Stock> findStock(@PathVariable long stockId);
    
    @PostMapping
    ResponseEntity<Stock> save(@RequestBody Stock stock);
    
    @PutMapping("/{stockId}")
    ResponseEntity<Stock> update(@RequestBody StockUpdate stock, @PathVariable long stockId);
    
    default ResponseEntity<Stock> fallbackAfterRetry(){
        System.out.println("Estoy en un fallback method");
        return ResponseEntity.badRequest().build();
    }
}
