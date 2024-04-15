
package com.curso.java.bookservice.client.stockservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(name = "stock-service", url = "http://localhost:8091", path = "/api/stocks")
public interface StockClient {
    
}
