package com.caddy.erasxchange.services.courses;

import com.caddy.erasxchange.models.course.ExternalCourse;
import com.caddy.erasxchange.models.users.Student;
import com.caddy.erasxchange.repositories.course.ExternalCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.Optional;

@Service
public class ExternalCourseService extends CourseService<ExternalCourse, ExternalCourseRepository> {

    @Autowired
    public ExternalCourseService(ExternalCourseRepository externalCourseRepository) {
        super(externalCourseRepository);
    }

    @Override
    protected String getClassName() {
        return ExternalCourse.class.getName();
    }




}
