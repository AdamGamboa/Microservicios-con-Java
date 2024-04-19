
package com.curso.java.securedrest.service;

import com.curso.java.securedrest.exceptions.NotFoundException;
import com.curso.java.securedrest.exceptions.ValidationException;
import com.curso.java.securedrest.model.Book;
import com.curso.java.securedrest.model.BookStatus;
import com.curso.java.securedrest.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    
    private final BookRepository bookRepository;
    
    @Autowired
    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }
    
    public List<Book> getActiveBooks(){
        return this.bookRepository.getByStatus(BookStatus.ACTIVE);
    }
    
    public Book find(long bookId){
        Optional<Book> bookOpt = this.bookRepository.find(bookId);
        return bookOpt.orElseThrow(() -> {
            return new NotFoundException(String.format("Book "+bookId+" not found"));
        });
    }
    
    public Book save(Book book){
        this.validate(book);
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
