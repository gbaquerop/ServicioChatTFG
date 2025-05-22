package com.example.serviciochat.service;

import com.example.serviciochat.model.User;
import com.example.serviciochat.repository.UserRepository;
import com.example.serviciochat.utils.EncriptationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        username = EncriptationHelper.desencriptarAES(username);
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return Optional.of(user);
        }
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Boolean login(String username, String password) {
        username = EncriptationHelper.desencriptarAES(username);
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user.getPassword().equals(password);
        }
        return false;
    }

    @Override
    public Boolean register(String username, String password) {
        username = EncriptationHelper.desencriptarAES(username);

        if (userRepository.findByUsername(username) == null) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
