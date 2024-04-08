package com.curso.java.principiosrest.repository;

import com.curso.java.principiosrest.model.Book;
import com.curso.java.principiosrest.model.User;
import com.curso.java.principiosrest.model.UserStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;


@Repository
public class UserRepository {
    private final Set<User> dataSource = new HashSet<>();
    private final AtomicLong sequence = new AtomicLong();
    private final Map<Long, List<Book>> userBookDataSource = new HashMap<>();
    
    
    public User save(User user){
        if(user.getId() == null){
            long newId = sequence.incrementAndGet();
            user.setId(newId);
        }
        dataSource.add(user);
        return user;
    }
    
    public Optional<User> find(long userId){
        return dataSource.stream().filter(user -> userId == user.getId()).findFirst();
    }
    
    public List<User> getByStatus(UserStatus status){
        return dataSource.stream()
                .filter(user -> status.equals(user.getStatus()))
                .toList();
    }
    
    public boolean delete(long userId){
        var user = find(userId);
        if(user.isPresent()){
            var userToDelete = user.get();
            userToDelete.setStatus(UserStatus.INACTIVE);
            return true;
        }
        return false;
    }
    
    public boolean addBookToUser(Long userId, Book book){
        List<Book> userBooks = this.userBookDataSource.get(userId);
        if(userBooks == null){
            userBooks = new ArrayList<>();
            this.userBookDataSource.put(userId, userBooks);
        }
        return userBooks.add(book);
    }
    
    public boolean removeBookFromUser(Long userId, Book book){
        List<Book> userBooks = this.userBookDataSource.get(userId);
        if(userBooks != null){
            return userBooks.remove(book);
        }
        return false;
    }
    
    public List<Book> getBooksFromUser(long userId){
        List<Book> books = this.userBookDataSource.get(userId);
        if(books == null){
            return new ArrayList<>();
        }
        return books;
    } 
}
        