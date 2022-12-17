package com.caddy.erasxchange.repositories.form;

import com.caddy.erasxchange.models.forms.Form;
import com.caddy.erasxchange.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface FormRepository<T extends Form> extends JpaRepository<T, Long> {
    List<T> findBySender(User sender);
}