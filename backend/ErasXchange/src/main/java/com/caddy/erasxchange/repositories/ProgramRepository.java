package com.caddy.erasxchange.repositories;

import com.caddy.erasxchange.models.forms.university.Program;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository<Program, Long> {
}