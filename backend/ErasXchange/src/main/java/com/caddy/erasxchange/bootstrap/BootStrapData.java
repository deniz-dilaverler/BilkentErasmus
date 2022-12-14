package com.caddy.erasxchange.bootstrap;

import com.caddy.erasxchange.models.Department;
import com.caddy.erasxchange.models.course.ExternalCourse;
import com.caddy.erasxchange.models.university.ErasmusUniversity;
import com.caddy.erasxchange.models.university.Program;
import com.caddy.erasxchange.repositories.ProgramRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

import static com.caddy.erasxchange.models.Department.CS;

@Component
public class BootStrapData {
    /*
    private static final Department[] departments = {
            Department.CS,
            Department.IAED,
            Department.EE,
            Department.ME,
            Department.IE,
            Department.MAN
    };
    private final ProgramRepository programRepository;

    public BootStrapData(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void  startUp() {

        System.out.println("Adding random data");
        Faker faker = new Faker();

        List<ErasmusUniversity> erasmusUniversities = new LinkedList<>();

        for(int i = 0; i < 8; i++) {
            ErasmusUniversity erasmusUni = new ErasmusUniversity();
            for (int j = 0; j < 3; j++ ) {
                Program program = new Program();
                program.setDepartment(departments[(i + j) % departments.length]);
                program.setQuota((int) (Math.floor(Math.random() * 5) + 1));
                program.setUniversity(erasmusUni);
                erasmusUni.getPrograms().add(program);
            }

            erasmusUni.setAllowence((int) (Math.floor(Math.random() * 400) + 200));
            erasmusUni.set

        }


    }

     */
}
