package com.curso.java.securedrest.repository;

import com.curso.java.securedrest.model.Book;
import com.curso.java.securedrest.model.BookStatus;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {
    
    
    private final Set<Book> dataSource = new HashSet<>();
    private final AtomicLong sequence = new AtomicLong();
    
    
    public Book save(Book book){
        if(book.getId() == null){
            long newId = sequence.incrementAndGet();
            book.setId(newId);
        }
        dataSource.add(book);
        return book;
    }
    
    public Optional<Book> find(long bookId){
        return dataSource.stream().filter(book -> bookId == book.getId()).findFirst();
    }
    
    public List<Book> getByStatus(BookStatus status){
        return dataSource.stream()
                .filter(book -> status.equals(book.getStatus()))
                .toList();
    }
    
    public boolean delete(long bookId){
        var book = find(bookId);
        if(book.isPresent()){
            var bookToDelete = book.get();
            bookToDelete.setStatus(BookStatus.INACTIVE);
            return true;
        }
        return false;
    }
    
}
