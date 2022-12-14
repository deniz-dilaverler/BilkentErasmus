package com.caddy.erasxchange.mappers;

import com.caddy.erasxchange.models.course.BilkentCourse;
import com.caddy.erasxchange.models.course.ExternalCourse;
import com.caddy.erasxchange.repositories.course.BilkentCourseRepository;
import com.caddy.erasxchange.repositories.course.ExternalCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/*
 this class was implemented because the course mappers needs these methods to turn ids to objects, however when I pass course services
 to the mappers, there becomes a circular dependency between ExternalCourseService, externalCOurseMapper, b,lkentCourseService, bilkentCourseMapper and ExternalCOurseService again
 */
@Component
public class CourseFromId {
    private final ExternalCourseRepository externalCourseRepository;

    private final BilkentCourseRepository bilkentCourseRepository;
    @Autowired
    public CourseFromId(ExternalCourseRepository externalCourseRepository, BilkentCourseRepository bilkentCourseRepository) {
        this.externalCourseRepository = externalCourseRepository;
        this.bilkentCourseRepository = bilkentCourseRepository;
    }

     ExternalCourse getExternalCourse(Long id) {
        Optional<ExternalCourse> optional = externalCourseRepository.findById(id);

        if (optional.isEmpty()) throw new ResourceNotFoundException(  "ExternalCourse with id: " + id + ",  not found called from: " + CourseFromId.class.getName()) ;
        else return optional.get();
    }

    BilkentCourse getBilkentCourse(Long id) {
        Optional<BilkentCourse> optional = bilkentCourseRepository.findById(id);

        if (optional.isEmpty()) throw new ResourceNotFoundException(  "ExternalCourse with id: " + id + ",  not found called from: " + CourseFromId.class.getName()) ;
        else return optional.get();
    }
}
