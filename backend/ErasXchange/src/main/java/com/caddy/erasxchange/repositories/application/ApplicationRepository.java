package com.caddy.erasxchange.repositories.application;

import com.caddy.erasxchange.models.Department;
import com.caddy.erasxchange.models.application.AppStatus;
import com.caddy.erasxchange.models.application.Application;
import com.caddy.erasxchange.models.application.ErasmusApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@NoRepositoryBean
public interface ApplicationRepository<T extends Application> extends JpaRepository<T, Long> {

    public List<T> findByStatus(AppStatus appStatus);

    public List<T> findByStatusAndStudentDepartment(AppStatus appStatus, Department department);

    List<T> findByStatusNotAndStudent_Department(AppStatus status, Department department);
}