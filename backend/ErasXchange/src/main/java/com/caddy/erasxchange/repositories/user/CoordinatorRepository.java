package com.caddy.erasxchange.repositories.user;

import com.caddy.erasxchange.models.Department;
import com.caddy.erasxchange.models.users.Coordinator;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoordinatorRepository extends UserRepository<Coordinator> {
    @Override
    <S extends Coordinator> List<S> saveAll(Iterable<S> entities);

    List<Coordinator> findByDepartment(Department cs);
}