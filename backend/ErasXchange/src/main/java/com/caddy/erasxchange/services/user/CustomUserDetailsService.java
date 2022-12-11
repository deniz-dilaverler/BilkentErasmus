package com.caddy.erasxchange.services.user;

import com.caddy.erasxchange.models.users.User;
import com.caddy.erasxchange.repositories.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.springframework.security.core.userdetails.User.withUsername;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository<User> userRepository;

    public CustomUserDetailsService(UserRepository<User> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByBilkentId(Integer.parseInt(username));
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        //org.springframework.security.core.userdetails.User.withUsername() builder
        return withUsername(user.getBilkentId().toString())
                .password(user.getPassword())
                .authorities(user.getRole().name())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}