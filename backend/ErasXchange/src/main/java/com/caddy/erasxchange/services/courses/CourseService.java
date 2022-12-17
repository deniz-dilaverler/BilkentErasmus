package com.caddy.erasxchange.services.courses;


import com.caddy.erasxchange.models.course.Course;
import com.caddy.erasxchange.repositories.course.CourseRepository;
import com.caddy.erasxchange.services.GenericService;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.Optional;

public abstract class   CourseService <C extends Course, R extends CourseRepository<C>>
                            extends GenericService<C, R> {


    public CourseService(R repository) {
        super(repository);
    }

    public C findById(Long id) {
        Optional<C> optional = repository.findById(id);

        if (optional.isEmpty()) throw new ResourceNotFoundException(getClassName() +  " with id: " + id + ",  not found") ;
        else return optional.get();
    }


    protected abstract String getClassName();
    
}
