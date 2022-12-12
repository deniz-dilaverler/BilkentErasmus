package com.caddy.erasxchange.repositories.user;

import com.caddy.erasxchange.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface UserRepository<T extends User> extends JpaRepository<T, Long> {

    public Optional<T> findUserByBilkentId(int bilkentId);

}
