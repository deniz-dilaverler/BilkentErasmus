package com.caddy.erasxchange.security;

import com.caddy.erasxchange.DTOs.LoginDto;
import com.caddy.erasxchange.models.users.Role;
import com.caddy.erasxchange.models.users.SecurityUser;
import com.caddy.erasxchange.models.users.User;
import com.caddy.erasxchange.repositories.SecurityUserRepository;
import com.caddy.erasxchange.repositories.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository<User> userRepository;
    private final AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;
    private final SecurityUserRepository securityUserRepository;

    public UserService(UserRepository<User> userRepository, AuthenticationManager authenticationManager, JwtProvider jwtProvider, SecurityUserRepository securityUserRepository) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.securityUserRepository = securityUserRepository;
    }

    public Optional<String> login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()));
        SecurityUser user = securityUserRepository.findByUsername(loginDto.getUsername());
        List<Role> roles  = new ArrayList<>();
        roles.add(user.getRole());
        Optional<String> token = Optional.of(jwtProvider.createToken(user.getUsername(), roles));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return token;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

}
