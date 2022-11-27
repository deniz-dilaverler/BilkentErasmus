package com.caddy.erasxchange.repositories.user;

import com.caddy.erasxchange.models.users.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository<T extends User> extends CrudRepository<T, Long> {

}
