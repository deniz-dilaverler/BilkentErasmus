package com.caddy.erasxchange.repositories;

import com.caddy.erasxchange.models.ApplicationState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationStateRepository extends JpaRepository<ApplicationState, Long> {
}