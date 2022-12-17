package com.caddy.erasxchange.repositories.user;

import com.caddy.erasxchange.models.Department;
import com.caddy.erasxchange.models.users.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends UserRepository<Student> {
    public List<Student > findAllByDepartment(Department department);
}
