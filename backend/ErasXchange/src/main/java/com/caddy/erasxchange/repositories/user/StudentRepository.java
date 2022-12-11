package com.caddy.erasxchange.repositories.user;

import com.caddy.erasxchange.models.users.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends UserRepository<Student> {
}
