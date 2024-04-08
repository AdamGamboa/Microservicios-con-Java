
package com.curso.java.principiosrest.service;

import com.curso.java.principiosrest.exceptions.NotFoundException;
import com.curso.java.principiosrest.exceptions.ValidationException;
import com.curso.java.principiosrest.model.Book;
import com.curso.java.principiosrest.model.User;
import com.curso.java.principiosrest.model.UserStatus;
import com.curso.java.principiosrest.repository.BookRepository;
import com.curso.java.principiosrest.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    
    @Autowired
    public UserService(UserRepository userRepository, BookRepository bookRepository){
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }
    
    public List<User> getActiveUsers(){
        return this.userRepository.getByStatus(UserStatus.ACTIVE);
    }
    
    public User find(long userId){
        Optional<User> userOpt = this.userRepository.find(userId);
        return userOpt.orElseThrow(() -> {
            return new NotFoundException(String.format("User "+userId+" not found"));
        });
    }
    
    public User save(User user){
        this.validate(user);
        return this.userRepository.save(user);
    }
    
    public User update(User user){
        this.validate(user);
        this.find(user.getId());
        return this.userRepository.save(user);
    }
    
    public void delete(long userId){
        this.userRepository.delete(userId);
    }
    
    public void addBookToUser(long userId, long bookId){
        this.find(userId);
        var book = this.bookRepository.find(bookId)
                .orElseThrow(() -> new NotFoundException("Book "+bookId+" not found"));
        this.userRepository.addBookToUser(userId, book);
    }
    
    public void removeBookFromUser(long userId, long bookId){
        this.find(userId);
        var book = this.bookRepository.find(bookId)
                .orElseThrow(() -> new NotFoundException("Book "+bookId+" not found"));
        this.userRepository.removeBookFromUser(userId, book);
    }
    
    public List<Book> getUserBooks(long userId){
        return this.userRepository.getBooksFromUser(userId);
    }
    
    private void validate(User user){
        if(user == null){
            throw new ValidationException("Not user indicated");
        }
        if(user.getName() == null || user.getName().isBlank()){
            throw new ValidationException("Name is required");
        }
        if(user.getLastName() == null || user.getLastName().isBlank()){
            throw new ValidationException("Last Name is required");
        }
    }
    
}
