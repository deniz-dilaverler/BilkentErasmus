package com.caddy.erasxchange.bootstrap;

import com.caddy.erasxchange.models.Department;
import com.caddy.erasxchange.models.Semester;
import com.caddy.erasxchange.models.university.ErasmusUniversity;
import com.caddy.erasxchange.models.university.Program;
import com.caddy.erasxchange.models.users.Coordinator;
import com.caddy.erasxchange.repositories.ProgramRepository;
import com.caddy.erasxchange.repositories.university.ErasmusUniversityRepository;
import com.caddy.erasxchange.repositories.user.CoordinatorRepository;
import com.caddy.erasxchange.services.user.CoordinatorService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class BootStrapData {

    private static final Department[] departments =Department.values();
    private final ProgramRepository programRepository;
    private final CoordinatorRepository coordinatorRepository;
    private final ErasmusUniversityRepository erasmusUniversityRepository;
    private final CoordinatorService coordinatorService;

    public BootStrapData(ProgramRepository programRepository, ErasmusUniversityRepository repository,
                         CoordinatorRepository coordinatorRepository, ErasmusUniversityRepository erasmusUniversityRepository, CoordinatorService coordinatorService) {
        this.programRepository = programRepository;
        this.coordinatorRepository = coordinatorRepository;
        this.erasmusUniversityRepository = erasmusUniversityRepository;
        this.coordinatorService = coordinatorService;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
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
        coordinator1.setFirstName("Can");
        coordinator1.setLastName("Alkan");
        coordinator1.setEmail("can.alkan@gmail.com");
        coordinator1.setDepartment(Department.CS);
        coordinator1.setPassword("12345");
        coordinator1.setBilkentId(222222222);
        coordinatorList.add(coordinator1);


        Coordinator coordinator2 = new Coordinator();
        coordinator1.setFirstName("Çiğdem");
        coordinator1.setLastName("Gündüz Demir");
        coordinator1.setEmail("çiğdem.demir@gmail.com");
        coordinator1.setDepartment(Department.CS);
        coordinator1.setPassword("12345");
        coordinator1.setBilkentId(11111111);
        coordinatorList.add(coordinator2);
        coordinatorRepository.saveAllAndFlush(coordinatorList);
        //coordinatorRepository.saveAll(coordinatorList);


        List<ErasmusUniversity> erasmusUniversities = new LinkedList<>();
        ErasmusUniversity erasmusUniversity1 = new ErasmusUniversity();
        erasmusUniversity1.setAllowence(400).setSemester(Semester.FALL).setName("Kingston University").setLanguageRequirement("English B2")
                .setCountry("United Kingdom").getCoordinators().add(coordinator1);
        Set<Program> programs = new HashSet<>();
        Program program = new Program().setDepartment(Department.CS).setQuota(4);
        programs.add(program);
        erasmusUniversity1.setPrograms(programs);

        erasmusUniversities.add(erasmusUniversity1);


        ErasmusUniversity erasmusUniversity2 = new ErasmusUniversity();
        erasmusUniversity2.setAllowence(300).setSemester(Semester.BOTH).setName("TU Vienna").setLanguageRequirement("German B2")
                .setCountry("Austria").getCoordinators().add(coordinator2);


        programs = new HashSet<>();
        program = new Program().setDepartment(Department.CS).setQuota(3);
        programs.add(program);
        erasmusUniversity2.setPrograms(programs);

        erasmusUniversities.add(erasmusUniversity2);


        ErasmusUniversity erasmusUniversity3 = new ErasmusUniversity();
        erasmusUniversity3.setAllowence(250).setSemester(Semester.BOTH).setName("University of Twente").setLanguageRequirement("English B2")
                .setCountry("Austria").getCoordinators().add(coordinator2);
        coordinator2.getResponsibleSchools().add(erasmusUniversity3);
        programs = new HashSet<>();
        program = new Program().setDepartment(Department.CS).setQuota(2);
        programs.add(program);

        erasmusUniversities.add(erasmusUniversity3);
        erasmusUniversityRepository.saveAllAndFlush(erasmusUniversities);



        ErasmusUniversity eras = erasmusUniversityRepository.findById(1L).get();
        System.out.println(eras.getCoordinators().size());

        Coordinator coordinator = coordinatorService.findById(1L);

        System.out.println(coordinator.getResponsibleSchools().size());



    }


}
