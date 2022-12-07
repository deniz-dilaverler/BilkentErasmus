package com.caddy.erasxchange.repositories.application;

import com.caddy.erasxchange.models.application.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ApplicationRepository<T extends Application> extends JpaRepository<T, Long> {
}