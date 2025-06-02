package com.example.serviciochat.utils;

import com.example.serviciochat.model.User;
import com.example.serviciochat.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "spring.jpa.hibernate.ddl-auto", havingValue = "create", matchIfMissing = false)
public class Populator {
    private final UserRepository userRepository;
    private final EncriptationHelper encriptationHelper;

    public Populator(UserRepository userRepository,
                      EncriptationHelper encriptationHelper) {
        this.userRepository = userRepository;
        this.encriptationHelper = encriptationHelper;
    }

    @PostConstruct
    public void populate() {
        User user1 = new User("Prueba1", encriptationHelper.encriptarAES("1234"));
        userRepository.save(user1);
        User user2 = new User("Prueba2", encriptationHelper.encriptarAES("1234"));
        userRepository.save(user2);
        User user3 = new User("Prueba3", encriptationHelper.encriptarAES("1234"));
        userRepository.save(user3);

    }
}
