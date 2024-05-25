package com.example.restservicecors.service.implementation;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.restservicecors.models.auth.AuthUser;
import com.example.restservicecors.models.user.User;
import com.example.restservicecors.payload.request.SignUpRequest;
import com.example.restservicecors.payload.request.SigninRequest;
import com.example.restservicecors.payload.response.JwtAuthenticationResponse;
import com.example.restservicecors.service.AuthenticationService;
import com.example.restservicecors.service.JwtService;
import com.example.restservicecors.service.respositories.AuthUserRepository;
import com.example.restservicecors.service.respositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthUserRepository authUserRespository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        // Step 1: Insert user information into auth.users table
        LocalDateTime currentDate = LocalDateTime.now();
        String role = "user";

        AuthUser user = AuthUser.builder()
                .email(request.getEmail())
                .encrypted_password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();
        authUserRespository.save(user);
        User existingUser = userRepository.findByUsername(request.getEmail());
        if (existingUser != null) {
            // Update existing user with first name and last name
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            existingUser.setPassword(user.getEncrypted_password());
            existingUser.setCreatedAt(currentDate);
            userRepository.save(existingUser);
        } else {
            userRepository.save(existingUser);
        }
        String jwt = jwtService.generateToken(user);
        Map<String, String> refreshToken = jwtService.generateRefreshToken(user);
        JwtAuthenticationResponse response = JwtAuthenticationResponse.builder()
                .user(existingUser)
                .access_token(jwt)
                .refresh_token(refreshToken)
                .build();

        return response;

    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        AuthUser user = authUserRespository.findByEmail(request.getEmail());
        User userData = userRepository.findByUsername(request.getEmail());
        String jwt = jwtService.generateToken(user);
        Map<String, String> refreshToken = jwtService.generateRefreshToken(user);
        JwtAuthenticationResponse response = JwtAuthenticationResponse.builder()
                .user(userData)
                .access_token(jwt)
                .refresh_token(refreshToken)
                .build();

        return response;
    }

    @Override
    public AuthUser signedInUser(SignUpRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        AuthUser user = authUserRespository.findByEmail(request.getEmail());
        return user;
    }

    @Override
    public AuthUser signedUpUser(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        AuthUser user = authUserRespository.findByEmail(request.getEmail());
        return user;
    }

}