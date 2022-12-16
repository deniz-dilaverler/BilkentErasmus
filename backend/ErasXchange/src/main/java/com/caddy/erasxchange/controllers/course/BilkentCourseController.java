package com.caddy.erasxchange.controllers.course;

import com.caddy.erasxchange.DTOs.BilkentCourseDto;
import com.caddy.erasxchange.services.courses.BilkentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course/bilkent")
public class BilkentCourseController {
    private final BilkentCourseService courseService;

    @Autowired
    public BilkentCourseController(BilkentCourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<BilkentCourseDto> getCourse(@PathVariable Long courseId) {
        BilkentCourseDto course = courseService.findCourseById(courseId);

        return new ResponseEntity<BilkentCourseDto>(course, HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<BilkentCourseDto>> getCourses() {
        List<BilkentCourseDto> courses = courseService.getCourses();

        return new ResponseEntity<List<BilkentCourseDto>>(courses, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity updateCoursesFromSrs() {
        courseService.updateSrsCourses();
        return new ResponseEntity(HttpStatus.OK);
    }

}
