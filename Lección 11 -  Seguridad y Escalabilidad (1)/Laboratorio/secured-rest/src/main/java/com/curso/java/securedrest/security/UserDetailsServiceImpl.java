package com.curso.java.securedrest.security;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final List<String> validUsers = List.of("john.smith", "jane.doe");
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(validUsers.contains(username));
        if(username != null && validUsers.contains(username)){
            return new UserPrincipal(username);
        }
        throw new UsernameNotFoundException("could not found user..!!");
    }
    
}
