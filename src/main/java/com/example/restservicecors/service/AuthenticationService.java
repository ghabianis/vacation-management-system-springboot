package com.example.restservicecors.service;

import com.example.restservicecors.models.auth.AuthUser;
import com.example.restservicecors.payload.request.SignUpRequest;
import com.example.restservicecors.payload.request.SigninRequest;
import com.example.restservicecors.payload.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);

    AuthUser signedInUser(SignUpRequest request);

    AuthUser signedUpUser(SigninRequest request);

}
