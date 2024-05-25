package com.example.restservicecors.service;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractEmailString(String token);

    String generateToken(UserDetails userDetails);

    Map<String, String> generateRefreshToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);
}