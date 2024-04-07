package com.curso.java.restapi.repository;

import com.curso.java.restapi.model.User;
import com.curso.java.restapi.model.UserStatus;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;


@Repository
public class UserRepository {
    private final Set<User> dataSource = new HashSet<>();
    private final AtomicLong sequence = new AtomicLong();
    
    
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
    
}
