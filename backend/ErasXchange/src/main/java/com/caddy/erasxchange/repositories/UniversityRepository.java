package com.caddy.erasxchange.repositories;

import com.caddy.erasxchange.models.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UniversityRepository extends JpaRepository<University, Long> {
    University findByName(String name);
}