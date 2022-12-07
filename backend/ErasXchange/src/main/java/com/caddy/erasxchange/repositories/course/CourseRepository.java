package com.caddy.erasxchange.repositories.course;

import com.caddy.erasxchange.models.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CourseRepository<T extends Course> extends JpaRepository<T, Long> {
}