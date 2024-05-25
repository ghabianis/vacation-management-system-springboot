package com.example.restservicecors.service.respositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.restservicecors.models.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String email);

    // Implementing getById
    User getById(String id);

    // Implementing
    List<User> findAll();

    // Implementing create
    <S extends User> S save(S user);

    // Implementing delete
    void deleteById(String id);

    boolean existsById(String id);
}