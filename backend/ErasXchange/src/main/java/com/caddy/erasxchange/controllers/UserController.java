package com.caddy.erasxchange.controllers;

import com.caddy.erasxchange.DTOs.LoginDto;
import com.caddy.erasxchange.models.users.User;
import com.caddy.erasxchange.security.AuthService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/login")
public class UserController {
    private final AuthService authService;

    public UserController( AuthService authService) {
        this.authService = authService;
    }

    @PostMapping()
    public ResponseTransfer login(@RequestBody LoginDto loginDto) {
        return new ResponseTransfer(authService.login(loginDto)) ;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void setNewPassword(@RequestParam String code, @RequestParam String pwd) {
        authService.resetPassword(code, pwd);
    }

    @GetMapping
    @PreAuthorize("hasRole('STUDENT')")
    public List<User> getAllUsers() {
        return authService.getAll();
    }
}
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class ResponseTransfer {
    private String authToken;

    // standard getters/setters
}