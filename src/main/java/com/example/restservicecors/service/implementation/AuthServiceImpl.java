package com.example.restservicecors.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.restservicecors.service.AuthService;
import com.example.restservicecors.service.respositories.AuthUserRepository;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthUserRepository authUserRespository;

    @Autowired
    public AuthServiceImpl(AuthUserRepository authUserRespository) {
        this.authUserRespository = authUserRespository;
    }

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) {
                return authUserRespository.findByEmail(email);
            }
        };
    }
}
