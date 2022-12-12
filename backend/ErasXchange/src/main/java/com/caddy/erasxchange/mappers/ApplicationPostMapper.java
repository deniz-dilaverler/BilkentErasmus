package com.caddy.erasxchange.mappers;

import com.caddy.erasxchange.DTOs.ApplicationPostDto;
import com.caddy.erasxchange.models.University;
import com.caddy.erasxchange.models.application.Application;
import com.caddy.erasxchange.models.application.BilateralApplication;
import com.caddy.erasxchange.models.application.ErasmusApplication;
import com.caddy.erasxchange.models.users.Student;
import com.caddy.erasxchange.services.UniversityService;
import com.caddy.erasxchange.services.user.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;


@Component
public class ApplicationPostMapper {

    final private StudentService studentService;
    final private UniversityService universityService;

    @Autowired
    public ApplicationPostMapper(StudentService studentService, UniversityService universityService) {
        this.studentService = studentService;
        this.universityService = universityService;
    }

    public ErasmusApplication  applicationPostToErasmusApplicationMapper(ApplicationPostDto applicationPostDto) {
        ErasmusApplication application = new ErasmusApplication();
        converter(applicationPostDto, application);
        return  application;
    }

    public BilateralApplication applicationPostToBilateralApplicationMapper(ApplicationPostDto applicationPostDto) {
        BilateralApplication application = new BilateralApplication();
        converter(applicationPostDto, application);
        return  application;
    }

    private void converter(ApplicationPostDto applicationPostDto, Application application) {
        application.setStatus(applicationPostDto.getStatus());
        application.setSemester(applicationPostDto.getSemester());
        Student student = null;
        if (applicationPostDto.getStudentId() != null)
            student = studentService.findById(applicationPostDto.getStudentId());

        application.setStudent(student);
        University university = null;
        if (applicationPostDto.getChoice1Id() != null) {
            university = universityService.findById(applicationPostDto.getChoice1Id());
            application.setChoice1(university);
        }
        if (applicationPostDto.getChoice1Id() != null) {
            university = universityService.findById(applicationPostDto.getChoice2Id());
            application.setChoice2(university);
        }
        if (applicationPostDto.getChoice1Id() != null) {
            university = universityService.findById(applicationPostDto.getChoice3Id());
            application.setChoice3(university);
        }
        if (applicationPostDto.getChoice1Id() != null) {
            university = universityService.findById(applicationPostDto.getChoice4Id());
            application.setChoice4(university);
        }
        if (applicationPostDto.getChoice1Id() != null) {
            university = universityService.findById(applicationPostDto.getChoice5Id());
            application.setChoice5(university);
        }
    }
}
