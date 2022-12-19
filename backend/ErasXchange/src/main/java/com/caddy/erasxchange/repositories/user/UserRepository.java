package com.caddy.erasxchange.repositories.user;

import com.caddy.erasxchange.models.Department;
import com.caddy.erasxchange.models.users.Coordinator;
import com.caddy.erasxchange.models.users.Role;
import com.caddy.erasxchange.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository<T extends User> extends JpaRepository<T, Long> {
    Optional<T> findByBilkentId(int bilkentId);
    Optional<T> findByFirstNameAndLastName(String name, String l);
    List<T> findByDepartment(Department department);

    List<T> findByDepartmentAndRole(Department department, Role role);

    T findByVerificationCode(String code);
}
