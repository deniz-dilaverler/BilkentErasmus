package com.caddy.erasxchange.repositories.form;

import com.caddy.erasxchange.models.forms.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface FormRepository<T extends Form> extends JpaRepository<T, Long> {
}