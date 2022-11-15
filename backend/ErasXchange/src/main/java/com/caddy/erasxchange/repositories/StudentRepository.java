package com.caddy.erasxchange.repositories;

import com.caddy.erasxchange.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}