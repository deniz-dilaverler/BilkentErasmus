package com.caddy.erasxchange.controllers;

import com.caddy.erasxchange.DTOs.LoginDto;
import com.caddy.erasxchange.models.users.User;
import com.caddy.erasxchange.security.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/login")
public class UserController {
    private final UserService userService;

    public UserController( UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public Optional<String> login(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto) ;
    }

    @GetMapping
    @PreAuthorize("hasRole('STUDENT')")
    public List<User> getAllUsers() {
        return userService.getAll();
    }
}
