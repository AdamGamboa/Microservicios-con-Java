
package com.curso.java.orderservice.client.userservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "user-service", url = "http://localhost:8091", path = "/api/users")
public interface UserClient {
    
    @GetMapping("/{userId}")
    ResponseEntity<User> findUser(@PathVariable Long userId);
}
