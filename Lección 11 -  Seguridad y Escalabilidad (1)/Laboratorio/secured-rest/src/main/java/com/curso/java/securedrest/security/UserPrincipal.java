package com.curso.java.securedrest.security;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {

    private final String username;

    public UserPrincipal(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("UserPrincipal.getAuthorities");
        return List.of();
    }

    @Override
    public String getPassword() {
        System.out.println("UserPrincipal.getPassword");
        return new StringBuilder(username).reverse().toString();
    }

    @Override
    public String getUsername() {
        System.out.println("UserPrincipal.getUsername");
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
