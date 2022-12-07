package com.caddy.erasxchange.repositories;

import com.caddy.erasxchange.models.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ProgramRepository extends JpaRepository<Program, Long> {
}