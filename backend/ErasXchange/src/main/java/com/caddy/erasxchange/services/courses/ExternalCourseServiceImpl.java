package com.caddy.erasxchange.services.courses;

import com.caddy.erasxchange.models.course.ExternalCourse;
import com.caddy.erasxchange.repositories.course.ExternalCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalCourseServiceImpl extends CourseService<ExternalCourse, ExternalCourseRepository> {

    @Autowired
    public ExternalCourseServiceImpl(ExternalCourseRepository externalCourseRepository) {
        super(externalCourseRepository);
    }


}
