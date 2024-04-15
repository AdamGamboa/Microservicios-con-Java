package com.curso.java.bookservice.controller;

import com.curso.java.bookservice.client.stockservice.StockUpdate;
import com.curso.java.bookservice.model.Book;
import com.curso.java.bookservice.service.BookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/books")
public class BookController {
    
    private final BookService bookService;
    
    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }
    
    @GetMapping
    public List<Book> getBooks(){
        var books = bookService.getActiveBooks();
        return books;
    }
    
    @GetMapping("/{bookId}")
    public Book findBook(@PathVariable long bookId){
        return bookService.find(bookId);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book save(@RequestBody Book book){
        return this.bookService.save(book);
    }
    
    @PutMapping("/{bookId}")
    public Book update(@RequestBody Book book, @PathVariable long bookId){
        if(bookId != book.getId()){
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "Book ids in Path and body are not the same");
        }
        return this.bookService.save(book);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long bookId){
        this.bookService.delete(bookId);
    }
    
    @PostMapping("/{bookId}/stock/")
    public Book updateStock(@RequestBody StockUpdate stock, @PathVariable Long bookId){
       this.bookService.updateStock(bookId, stock);
       return this.bookService.find(bookId);
    }
}
