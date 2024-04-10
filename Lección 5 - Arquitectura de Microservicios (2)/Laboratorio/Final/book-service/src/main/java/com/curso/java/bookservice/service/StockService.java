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
    
    
    @Retry(name = "retryFindStock", fallbackMethod = "findStockFallbackRetry")
    //@CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "findStockFallbackCircuitBreaker" )
    public Stock find(Long stockId){
        System.out.println("llamando a FindStock(stockId:"+stockId+")");
        var stockResponse = stockClient.findStock(stockId);
        if(stockResponse.getStatusCode().equals(HttpStatusCode.valueOf(200))){
            var stock = stockResponse.getBody();
            cache.put(stock.getId(), stock);
            return stock;
        }
        return null;
    }
    
    public Stock save(Stock stock){
        var stockResponse = stockClient.save(stock);
        if(stockResponse.getStatusCode().equals(HttpStatusCode.valueOf(201))){
            return stockResponse.getBody();
        }
        return null;
    }
    
    public Stock update(StockUpdate stock, long stockId){
        var stockResponse = stockClient.update(stock, stockId);
        if(stockResponse.getStatusCode().equals(HttpStatusCode.valueOf(200))){
            return stockResponse.getBody();
        }
        return null;
    }
    
    private Stock findStockFallbackRetry(Long stockId, Exception ex){
        System.out.println("... Ejecutando Fallback por Retries findStock(stockId:"+stockId+")");
        var stockInCache = cache.get(stockId);
        if(stockInCache != null){
            System.out.println("... Obteniendo Stock desde caché debido a Retries");
        }
        return stockInCache;
    }
    
    private Stock findStockFallbackCircuitBreaker(Long stockId, Exception ex){
        System.out.println("... Ejecutando Fallback por Circuit Breaker findStock(stockId:"+stockId+")");
        var stockInCache = cache.get(stockId);
        if(stockInCache != null){
            System.out.println("... Obteniendo Stock desde caché debido a CircuitBreaker");
        }
        return stockInCache;
    }
    
}
