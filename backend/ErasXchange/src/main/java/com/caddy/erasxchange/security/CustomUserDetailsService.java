package com.caddy.erasxchange.security;

import com.caddy.erasxchange.models.users.Role;
import com.caddy.erasxchange.models.users.SecurityUser;
import com.caddy.erasxchange.repositories.SecurityUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final SecurityUserRepository userRepository;

    JwtProvider jwtProvider;

    public CustomUserDetailsService(SecurityUserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUser user = this.userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("username " + username + " is not found");
        }
        List<Role> roles = new ArrayList<>();
        roles.add(user.getRole());
        return new User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(roles));
    }

    public Optional<UserDetails> loadUserByJwtToken(String jwtToken) {
        if (jwtProvider.isValidToken(jwtToken)) {
            return Optional.of(new User(jwtProvider.getUsername(jwtToken), "", jwtProvider.getRoles(jwtToken)));
        }
        return Optional.empty();
    }

    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(List<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
    }
}