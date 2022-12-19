package com.caddy.erasxchange.services;

import com.caddy.erasxchange.models.Event;
import com.caddy.erasxchange.models.users.User;
import com.caddy.erasxchange.repositories.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class EventService {
    private final UserRepository<User> userRepository;

    public EventService(UserRepository<User> userRepository) {
        this.userRepository = userRepository;
    }


    public void sendEvent(User user, String title, String desc) {
        user.getEvents().add(new Event(Instant.now(), title, desc));
        userRepository.save(user);
    }
}
