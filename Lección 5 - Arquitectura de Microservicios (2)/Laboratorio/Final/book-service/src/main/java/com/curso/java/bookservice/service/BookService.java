
package com.curso.java.bookservice.service;

import com.curso.java.bookservice.client.stockservice.Stock;
import com.curso.java.bookservice.client.stockservice.StockClient;
import com.curso.java.bookservice.client.stockservice.StockUpdate;
import com.curso.java.bookservice.exceptions.NotFoundException;
import com.curso.java.bookservice.exceptions.ValidationException;
import com.curso.java.bookservice.model.Book;
import com.curso.java.bookservice.model.BookStatus;
import com.curso.java.bookservice.repository.BookRepository;
import feign.RetryableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    
    private final BookRepository bookRepository;
    
    private final StockClient stockClient;
    
    @Autowired
    public BookService(BookRepository bookRepository, StockClient stockClient){
        this.bookRepository = bookRepository;
        this.stockClient =  stockClient;
    }
    
    public List<Book> getActiveBooks(){
        return this.bookRepository.getByStatus(BookStatus.ACTIVE)
                .stream()
                .peek(book -> {
                    if(book.getStock()!= null){
                        var stock = this.findStock(book.getStock().getId());
                        if(stock != null){
                            book.setStock(stock);
                        }
                    }
                })
                .toList();
    }
    
    public Book find(long bookId){
        Optional<Book> bookOpt = this.bookRepository.find(bookId);
        var book = bookOpt.orElseThrow(() -> {
            return new NotFoundException(String.format("Book "+bookId+" not found"));
        });
        
        if(book.getStock()!= null){
            var stock = this.findStock(book.getStock().getId());
            if(stock != null){
                book.setStock(stock);
            }
        }
        return book;
    }
    
    public Book save(Book book){
        this.validate(book);
        var stock = new Stock();
        stock.setCurrentQuantity(10);
        var stockResponse = stockClient.save(stock);
        if(stockResponse.getStatusCode().equals(HttpStatusCode.valueOf(201))){
            book.setStock(stockResponse.getBody());
        }
        return this.bookRepository.save(book);
    }
    
    public Book update(Book book){
        this.validate(book);
        this.find(book.getId());
        return this.bookRepository.save(book);
    }
    
    public void delete(long bookId){
        this.bookRepository.delete(bookId);
    }
    
    public void updateStock(long bookId, StockUpdate stock){
        var book = this.find(bookId);
        
        if(book.getStock() != null){
            var stockResponse = stockClient.update(stock, book.getStock().getId());
            if(stockResponse.getStatusCode().equals(HttpStatusCode.valueOf(200))){
                book.setStock(stockResponse.getBody());
            }
        }
    }
    
    private void validate(Book book){
        if(book == null){
            throw new ValidationException("Not book indicated");
        }
        if(book.getTitle()== null || book.getTitle().isBlank()){
            throw new ValidationException("Title is required");
        }
        if(book.getSummary()== null || book.getSummary().isBlank()){
            throw new ValidationException("Summary is required");
        }
        if(book.getYear() == null){
            throw new ValidationException("Year is required");
        }
    }
    
    @Retry(name = "retryFindStock", fallbackMethod = "fallBackAfterRetryFindStock")
    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "fallBackAfterCircuitBreakerFindStock" )
    public Stock findStock(Long stockId){
        System.out.println("llamando a FindStock("+stockId+")");
        var stockResponse = stockClient.findStock(stockId);
        if(stockResponse.getStatusCode().equals(HttpStatusCode.valueOf(200))){
            return stockResponse.getBody();
        }
        return null;
    }
    
    public Stock fallBackAfterRetryFindStock(Long stockId, Exception ex){
        System.out.println("... Obteniendo Stock desde caché");
        return null;
    }
    
    public Stock fallBackAfterCircuitBreakerFindStock(long stockId, Exception ex){
        System.out.println("... Obteniendo Stock desde caché");
        return null;
    }
    
}
