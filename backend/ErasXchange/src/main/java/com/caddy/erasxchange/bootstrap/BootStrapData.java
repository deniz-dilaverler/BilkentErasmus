package com.caddy.erasxchange.bootstrap;

import com.caddy.erasxchange.models.Department;
import com.caddy.erasxchange.models.users.Coordinator;
import com.caddy.erasxchange.repositories.ProgramRepository;
import com.caddy.erasxchange.repositories.university.ErasmusUniversityRepository;
import com.caddy.erasxchange.repositories.user.CoordinatorRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BootStrapData {

    private static final Department[] departments =Department.values();
    private final ProgramRepository programRepository;
    private final CoordinatorRepository coordinatorRepository;
    private final ErasmusUniversityRepository erasmusUniversityRepository;

    public BootStrapData(ProgramRepository programRepository, ErasmusUniversityRepository repository,
                         CoordinatorRepository coordinatorRepository, ErasmusUniversityRepository erasmusUniversityRepository) {
        this.programRepository = programRepository;
        this.coordinatorRepository = coordinatorRepository;
        this.erasmusUniversityRepository = erasmusUniversityRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void  startUp() {
        /*
        System.out.println("Adding random data");
        Faker faker = new Faker();

        List<ErasmusUniversity> erasmusUniversities = new LinkedList<>();

        for(int i = 0; i < 8; i++) {
            ErasmusUniversity erasmusUni = new ErasmusUniversity();
            Set<Program> programs = new HashSet<>();
            for (int j = 0; j < 3; j++ ) {
                Program program = new Program();
                program.setDepartment(departments[(i + j) % departments.length]);
                program.setQuota((int) (Math.floor(Math.random() * 5) + 1));
                program.setUniversity(erasmusUni);
                programs.add(program);
            }
            erasmusUni.setPrograms(programs);
            erasmusUni.setAllowence((int) (Math.floor(Math.random() * 400) + 200));
            erasmusUni.setCountry(String.valueOf(faker.country()));
            erasmusUni.setLanguageRequirement("English B2");
            erasmusUni.setName("uni");
            erasmusUni.setSemester(Semester.BOTH);
            erasmusUniversities.add(erasmusUni);
        }

        erasmusUniversityRepository.saveAll(erasmusUniversities);

        */
        List<Coordinator> coordinatorList = new ArrayList<>();
        Coordinator coordinator1 = new Coordinator();
        coordinator1.setFirstName("asd");
        coordinatorList.add(coordinator1);

        Coordinator c = new Coordinator();
        c.setFirstName("AA");
        coordinatorList.add(c);

        coordinatorRepository.saveAll(coordinatorList);

    }


}
