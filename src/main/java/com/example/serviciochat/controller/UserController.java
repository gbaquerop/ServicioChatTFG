package com.example.serviciochat.controller;

import com.example.serviciochat.model.User;
import com.example.serviciochat.service.IUserService;
import com.example.serviciochat.utils.EncriptationHelper;
import com.example.serviciochat.utils.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {
    private IUserService userService;
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login/{username}/{password}")
    public ResponseEntity<String> Login(@PathVariable String username, @PathVariable String password) {
        Boolean login = userService.login(username, password);
        return ResponseEntity.ok(EncriptationHelper.encriptarAES(login.toString()));
    }

    @GetMapping("/register/{username}/{password}")
    public ResponseEntity<String> Register(@PathVariable String username, @PathVariable String password) {
        Boolean register = userService.register(username, password);
        return ResponseEntity.ok(EncriptationHelper.encriptarAES(register.toString()));
    }

    @GetMapping("/DameDatosUsuario/{username}")
    public ResponseEntity<User> DameDatos(@PathVariable String username) {
        Optional<User> byUsername = userService.findByUsername(username);
        if (byUsername.isPresent()) {
            User user = byUsername.get();
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/DameUsuariosChat")
    public ResponseEntity<List<User>> DameUsuariosChat() {
        List<User> all = userService.findAll();
        return ResponseEntity.ok(all);
    }
}
