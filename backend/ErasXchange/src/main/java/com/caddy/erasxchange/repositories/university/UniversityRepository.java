package com.caddy.erasxchange.repositories.university;

import com.caddy.erasxchange.models.university.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface UniversityRepository<Uni extends University> extends JpaRepository<Uni, Long> {

    Uni findByName(String name);

}