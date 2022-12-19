package com.caddy.erasxchange.external_system_sim;

import com.caddy.erasxchange.DTOs.BilkentCoursePostDto;
import com.caddy.erasxchange.models.Department;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is simply a simulator for fetching bilkent course data from the SRS servers
 */
@Service
public class SrsCourseFetch {
    final List<BilkentCoursePostDto> courses;


    public SrsCourseFetch() {
        courses = new ArrayList<BilkentCoursePostDto>();
        initializeCourses();
    }

    public List<BilkentCoursePostDto> getCourses() {
        return courses;
    }

    private void initializeCourses() {
        BilkentCoursePostDto newCourse = new BilkentCoursePostDto();
        newCourse.setCourseCode("CS319").setEcts(6.5).setNormalCredit(4.0)
                .setDepartment(Department.CS).setName("Object Oriented Software Engineering")
                .setCoordinatorMail("eray.tüzün@gmail.com").setCoordinatorName("Eray Tüzün");
        courses.add(newCourse);
        newCourse = null;

        newCourse = new BilkentCoursePostDto();
        newCourse.setCourseCode("CS315").setEcts(5.0).setNormalCredit(3.0)
                .setDepartment(Department.CS).setName("Programming Languages")
                .setCoordinatorMail("altay.güvenir@gmail.com").setCoordinatorName("Altay Güvenir");
        courses.add(newCourse);
        newCourse = null;

        newCourse = new BilkentCoursePostDto();
        newCourse.setCourseCode("MATH230").setEcts(5.0).setNormalCredit(3.0)
                .setDepartment(Department.MATH).setName("Probability and Statistics")
                .setCoordinatorMail("dilek.koksal@gmail.com").setCoordinatorName("Dilek Köksal");
        courses.add(newCourse);
        newCourse = null;

        newCourse = new BilkentCoursePostDto();
        newCourse.setCourseCode("CS342").setEcts(6.5).setNormalCredit(4.0)
                .setDepartment(Department.CS).setName("Operating Systems")
                .setCoordinatorMail("ibrahim.korpeoglu@gmail.com").setCoordinatorName("Ibrahim Korpeoglu");
        courses.add(newCourse);
        newCourse = null;

        newCourse = new BilkentCoursePostDto();
        newCourse.setCourseCode("CS353").setEcts(5.0).setNormalCredit(3.0)
                .setDepartment(Department.CS).setName("Database Systems")
                .setCoordinatorMail("ozgur.ulusoy@gmail.com").setCoordinatorName("Özgür Ulusoy");
        courses.add(newCourse);
        newCourse = null;

        newCourse = new BilkentCoursePostDto();
        newCourse.setCourseCode("CS101").setEcts(6.5).setNormalCredit(4.0)
                .setDepartment(Department.CS).setName("Algorithms and Programming I")
                .setCoordinatorMail("david.davenport@gmail.com").setCoordinatorName("David Davenport");
        courses.add(newCourse);
        newCourse = null;

        newCourse = new BilkentCoursePostDto();
        newCourse.setCourseCode("CS473").setEcts(5.0).setNormalCredit(3.0)
                .setDepartment(Department.CS).setName("Algorithms 1")
                .setCoordinatorMail("ugur.dogrusoz@gmail.com").setCoordinatorName("Uğur Doğrusöz");
        courses.add(newCourse);
        newCourse = null;


        newCourse = new BilkentCoursePostDto();
        newCourse.setCourseCode("CS4xx").setEcts(5.0).setNormalCredit(3.0)
                .setDepartment(Department.CS).setName("CS Technical Elective")
                .setCoordinatorMail(null).setCoordinatorName(null);
        courses.add(newCourse);
        newCourse = null;


    }
}
