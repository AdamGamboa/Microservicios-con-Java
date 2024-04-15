
package com.curso.java.bookservice.service;

import com.curso.java.bookservice.client.stockservice.Stock;
import com.curso.java.bookservice.client.stockservice.StockUpdate;
import com.curso.java.bookservice.exceptions.NotFoundException;
import com.curso.java.bookservice.exceptions.ValidationException;
import com.curso.java.bookservice.model.Book;
import com.curso.java.bookservice.model.BookStatus;
import com.curso.java.bookservice.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    
    private final BookRepository bookRepository;
    
    private final StockService stockService;
    
    @Autowired
    public BookService(BookRepository bookRepository, StockService stockService){
        this.bookRepository = bookRepository;
        this.stockService =  stockService;
    }
    
    public List<Book> getActiveBooks(){
        return this.bookRepository.getByStatus(BookStatus.ACTIVE)
                .stream()
                .peek(book -> {
                    if(book.getStock()!= null){
                        var stock = this.stockService.find(book.getStock().getId());
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
            var stock = this.stockService.find(book.getStock().getId());
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
        var stockResponse = stockService.save(stock);
        if(stockResponse != null){
            book.setStock(stockResponse);
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
            var stockResponse = stockService.update(stock, book.getStock().getId());
            if(stockResponse != null){
                book.setStock(stockResponse);
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
    
    
}
