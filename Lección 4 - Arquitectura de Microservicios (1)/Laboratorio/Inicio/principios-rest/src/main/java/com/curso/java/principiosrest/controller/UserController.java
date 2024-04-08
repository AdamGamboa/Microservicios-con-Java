package com.curso.java.principiosrest.controller;

import com.curso.java.principiosrest.model.Book;
import com.curso.java.principiosrest.model.User;
import com.curso.java.principiosrest.service.UserService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
    
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/getUsers")
    public List<User> getUsers(){
        var users = userService.getActiveUsers();
        return users;
    }
    
    @GetMapping("/getUser")
    public User findUser(@RequestParam long userId){
        return userService.find(userId);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public User save(@RequestBody User user){
        return this.userService.save(user);
    }
    
    @PutMapping("/update-user/")
    public User update(@RequestBody User user, @RequestParam long userId){
        if(userId != user.getId()){
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "User ids in Path and body are not the same");
        }
        return this.userService.save(user);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") long userId){
        this.userService.delete(userId);
    }
    
    
    @PostMapping("/{userId}/listBooks")
    public List<Book> getUserBooks(Long userId){
        return this.userService.getUserBooks(userId);
    }
    
    @GetMapping("/{userId}/add/books/{bookId}")
    public void addBookToUser(Long userId, long bookId){
        this.userService.addBookToUser(userId, bookId);
    }
    
    @DeleteMapping("/{userId}/remove/books/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeBookFromUser(Long userId, long bookId){
        this.userService.removeBookFromUser(userId, bookId);
    }
}
