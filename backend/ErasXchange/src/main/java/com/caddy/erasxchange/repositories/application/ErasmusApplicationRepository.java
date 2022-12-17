package com.caddy.erasxchange.repositories.application;

import com.caddy.erasxchange.models.application.Application;
import com.caddy.erasxchange.models.application.ErasmusApplication;
import com.caddy.erasxchange.models.users.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface ErasmusApplicationRepository extends ApplicationRepository<ErasmusApplication> {
    ErasmusApplication findByStudent(Student student);
}