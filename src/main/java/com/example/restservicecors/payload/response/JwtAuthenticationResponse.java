package com.example.restservicecors.payload.response;

import java.time.Instant;
import java.util.Map;

import com.example.restservicecors.models.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private String access_token;
    private Instant accessTokenExpiration;

    private Map<String, String> refresh_token;
    private User user;
}