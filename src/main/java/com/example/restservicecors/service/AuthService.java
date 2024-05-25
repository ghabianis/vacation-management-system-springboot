package com.example.restservicecors.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService {

    UserDetailsService userDetailsService();
    
}
