package com.caddy.erasxchange.repositories.university;

import com.caddy.erasxchange.models.university.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@NoRepositoryBean
public interface UniversityRepository<Uni extends University> extends JpaRepository<Uni, Long> {

}