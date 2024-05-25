package com.example.restservicecors.service;

import java.util.List;

import com.example.restservicecors.models.user.User;

public interface UserService {
    User getById(final String id);

    User getByUsername(final String username);

    List<User> findAll();

    User save(final User user);

    User update(final User user, final String id);

    void delete(final String id);

}
