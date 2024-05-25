package com.example.restservicecors.service.implementation;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.restservicecors.models.user.User;
import com.example.restservicecors.service.UserService;
import com.example.restservicecors.service.respositories.AuthUserRepository;
import com.example.restservicecors.service.respositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthUserRepository authUserRespository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthUserRepository authUserRespository) throws Exception {
        this.userRepository = userRepository;
        this.authUserRespository = authUserRespository;
    }

    @Override
    @Transactional
    public User save(User user) {
        user.setCreatedAt(user.getCreatedAt());
        user.setUpdatedAt(user.getUpdatedAt());
        user.setDeletedAt(null);
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setRoles(user.getRoles());
        user.setPassword(user.getPassword());
        user.setUsername(user.getUsername());
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User getById(String id) {
        return userRepository.findById(id).get();// Return null if user not found, handle accordingly
    }

    @Override
    @Transactional
    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User update(final User user, final String id) {
        User existingUser = userRepository.findById(id).get();
        if (existingUser != null) {
            // Update the existing user with the new user details
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            existingUser.setRoles(user.getRoles());
            // Save the updated user
            userRepository.save(existingUser);
        } else {
            throw new ResponseStatusException(null, "user not exist");
        }
        return existingUser;
    }

    @Override
    @Transactional
    public void delete(String id) {
        userRepository.deleteById(id);
        UUID uuid = UUID.fromString(id);
        authUserRespository.deleteById(uuid);
    }

}
