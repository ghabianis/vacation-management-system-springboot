package com.example.restservicecors.service.respositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restservicecors.models.auth.AuthUser;

public interface AuthUserRespository extends JpaRepository<AuthUser, String> {

    AuthUser findByEmail(String email);

    // Implementing getById
    AuthUser getById(String id);

    // Implementing
    List<AuthUser> findAll();

    // Implementing create
    AuthUser save(AuthUser user);

    // Implementing update
    // <S extends AuthUser> S update(S user);

    // Implementing delete
    void deleteById(String id);

    boolean existsById(String id);
}
