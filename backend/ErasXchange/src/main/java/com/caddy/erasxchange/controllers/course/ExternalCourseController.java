package com.caddy.erasxchange.controllers.course;

import com.caddy.erasxchange.DTOs.ExternalCourseDto;
import com.caddy.erasxchange.DTOs.ExternalCoursePostDto;
import com.caddy.erasxchange.services.courses.ExternalCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course/external")
public class ExternalCourseController {

    private final ExternalCourseService  courseService;

    @Autowired
    public ExternalCourseController(ExternalCourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<ExternalCourseDto> getCourse(@PathVariable Long id) {
        ExternalCourseDto courseDto = courseService.getCourse(id);

        return  new ResponseEntity<ExternalCourseDto>(courseDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ExternalCourseDto>> getCourses() {
        List<ExternalCourseDto> courses = courseService.getCourses();

        return  new ResponseEntity<List<ExternalCourseDto>>(courses, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity addCourse(ExternalCoursePostDto externalCoursePostDto) {
        courseService.addCourse(externalCoursePostDto);

        return new ResponseEntity(HttpStatus.OK);
    }

    // this is only for admins and testing
    @PostMapping("/admin/add")
    public ResponseEntity addCourseDirectly(ExternalCourseDto courseDto) {
        courseService.addCourseDirectly(courseDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    // only for admins and testing
    @PutMapping("/admin/add/equivalence")
    public ResponseEntity addEquivalenceDirectly(Long externalCourseId, Long equivalentCourseId) {
        courseService.addEquivalenceDirectly(externalCourseId, equivalentCourseId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
