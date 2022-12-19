package com.caddy.erasxchange.services.courses;

import com.caddy.erasxchange.DTOs.BilkentCourseDto;
import com.caddy.erasxchange.DTOs.BilkentCoursePostDto;
import com.caddy.erasxchange.external_system_sim.SrsCourseFetch;
import com.caddy.erasxchange.mappers.BilkentCourseMapper;
import com.caddy.erasxchange.models.course.BilkentCourse;
import com.caddy.erasxchange.repositories.course.BilkentCourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BilkentCourseService extends CourseService<BilkentCourse, BilkentCourseRepository> {

    final SrsCourseFetch srsCourseFetch;
    final BilkentCourseMapper courseMapper;

    @Autowired
    public BilkentCourseService(BilkentCourseRepository repository, SrsCourseFetch srsCourseFetch, BilkentCourseMapper courseMapper) {
        super(repository);
        this.srsCourseFetch = srsCourseFetch;
        this.courseMapper = courseMapper;
        // update the courses from srs
        setCoursesFromSrs();
    }

    @Override
    protected String getClassName() {


        return BilkentCourse.class.getName();
    }

    public BilkentCourseDto findCourseById(Long id) {
        return courseMapper.toDto(super.findById(id));
    }

    public List<BilkentCourseDto> getCourses() {
        return  courseMapper.toDtoList(super.findAll());
    }


    public void updateSrsCourses() {
        setCoursesFromSrs();
    }

    /**
     * fetches bilkent courses from the srs system (currently simulated internal system)
     * if a course with the same course code exists in the database, updates the contents of the course entity
     * if there is no course adds it to the database
     */
    private void setCoursesFromSrs() {
        log.info("Fetching bilkentCourse data from srs");

        List<BilkentCoursePostDto> srsCourses = srsCourseFetch.getCourses();
        List<BilkentCourse> updatedCourses = new ArrayList<>();
        // if bilkent course with the same course code exists update it, if not add it to the system
        for(BilkentCoursePostDto srsCourse : srsCourses) {
            Optional<BilkentCourse> optional = repository.findCourseByCourseCode(srsCourse.getCourseCode());
            if(optional.isEmpty()) {
                updatedCourses.add(courseMapper.postToEntity(srsCourse) );
            } else {
                updatedCourses.add(courseMapper.postPartialUpdate( srsCourse,optional.get()));
            }
        }
        if(updatedCourses.size() >0) {
            repository.saveAll(updatedCourses);
        }
    }
}
