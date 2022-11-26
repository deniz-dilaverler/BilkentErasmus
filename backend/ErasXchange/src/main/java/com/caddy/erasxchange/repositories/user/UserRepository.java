package com.caddy.erasxchange.repositories.user;

import com.caddy.erasxchange.models.Users.Student;
import com.caddy.erasxchange.models.Users.User;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.util.Optional;

public interface UserRepository<T extends User> extends CrudRepository<T, Long> {

    Optional<T> findByBilkentId(Integer bilkentId);
}
