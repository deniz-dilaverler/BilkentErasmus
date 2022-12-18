package com.caddy.erasxchange.controllers;

import com.caddy.erasxchange.DTOs.LoginDto;
import com.caddy.erasxchange.models.users.User;
import com.caddy.erasxchange.security.AuthService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/login")
public class UserController {
    private final AuthService authService;

    public UserController( AuthService authService) {
        this.authService = authService;
    }

    @PostMapping()
    public Optional<String> login(@RequestBody LoginDto loginDto) {
        return authService.login(loginDto) ;
    }

    @GetMapping
    @PreAuthorize("hasRole('STUDENT')")
    public List<User> getAllUsers() {
        return authService.getAll();
    }
}
