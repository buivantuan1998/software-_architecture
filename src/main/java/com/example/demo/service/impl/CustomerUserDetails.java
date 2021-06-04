package com.example.demo.service.impl;

import com.example.demo.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

public class CustomerUserDetails implements UserDetails {

    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public static CustomerUserDetails fromUserEntityToCustomUserDetails(User userEntity) {
        CustomerUserDetails c = new CustomerUserDetails();
        c.email = userEntity.getEmail();
        c.password = userEntity.getPassword();
        c.grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(userEntity.getRole().getRoleName()));
        return c;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
