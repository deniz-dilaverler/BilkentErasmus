package com.caddy.erasxchange.services.courses;


import com.caddy.erasxchange.models.course.Course;
import com.caddy.erasxchange.repositories.course.CourseRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.Optional;

public abstract class   CourseService <C extends Course, R extends CourseRepository<C>>{

    R courseRepository ;

    protected CourseService(R courseRepository) {
        this.courseRepository = courseRepository;
    }

    public C getCourseById(Long id) {
        Optional<C> optional = courseRepository.findById(id);

        if (optional.isEmpty()) throw new ResourceNotFoundException("Course with id: " + id + ",  not found") ;
        else return optional.get();
    }
    
}
