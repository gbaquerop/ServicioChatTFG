package com.example.serviciochat.service;

import org.springframework.stereotype.Service;
import com.example.serviciochat.model.User;

import java.util.List;
import java.util.Optional;

@Service
public interface IUserService {
    User findById(Long id);
    Optional<User> findByUsername(String username);
    User save(User user);
    void delete(Long id);
    List<User> findAll();
    Boolean login(String username, String password);
    Boolean register(String username, String password);
}
