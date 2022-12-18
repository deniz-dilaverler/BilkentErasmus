package com.caddy.erasxchange.bootstrap;

import com.caddy.erasxchange.models.Department;
import com.caddy.erasxchange.models.Semester;
import com.caddy.erasxchange.models.course.ApprovalStatus;
import com.caddy.erasxchange.models.course.BilkentCourse;
import com.caddy.erasxchange.models.course.EquivalenceItem;
import com.caddy.erasxchange.models.course.ExternalCourse;
import com.caddy.erasxchange.models.university.ErasmusUniversity;
import com.caddy.erasxchange.models.university.Program;
import com.caddy.erasxchange.models.university.University;
import com.caddy.erasxchange.models.users.Coordinator;
import com.caddy.erasxchange.models.users.Iso;
import com.caddy.erasxchange.models.users.Role;
import com.caddy.erasxchange.models.users.User;
import com.caddy.erasxchange.repositories.ProgramRepository;
import com.caddy.erasxchange.repositories.course.BilkentCourseRepository;
import com.caddy.erasxchange.repositories.course.EquivalenceItemRepository;
import com.caddy.erasxchange.repositories.university.ErasmusUniversityRepository;
import com.caddy.erasxchange.repositories.user.CoordinatorRepository;
import com.caddy.erasxchange.repositories.user.UserRepository;
import com.caddy.erasxchange.services.courses.BilkentCourseService;
import com.caddy.erasxchange.services.user.CoordinatorService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class BootStrapData {

    private static final Department[] departments =Department.values();
    private final ProgramRepository programRepository;
    private final CoordinatorRepository coordinatorRepository;
    private final ErasmusUniversityRepository erasmusUniversityRepository;
    private final CoordinatorService coordinatorService;
    private final EquivalenceItemRepository equivalenceItemRepository;
    private final BilkentCourseRepository bilkentCourseRepository;
    private final BilkentCourseService bilkentCourseService;
    private final UserRepository<User> userRepository;

    public BootStrapData(ProgramRepository programRepository, ErasmusUniversityRepository repository,
                         CoordinatorRepository coordinatorRepository, ErasmusUniversityRepository erasmusUniversityRepository, CoordinatorService coordinatorService, EquivalenceItemRepository equivalenceItemRepository, BilkentCourseRepository bilkentCourseRepository, BilkentCourseService bilkentCourseService,
                         UserRepository userRepository) {
        this.programRepository = programRepository;
        this.coordinatorRepository = coordinatorRepository;
        this.erasmusUniversityRepository = erasmusUniversityRepository;
        this.coordinatorService = coordinatorService;
        this.equivalenceItemRepository = equivalenceItemRepository;
        this.bilkentCourseRepository = bilkentCourseRepository;
        this.bilkentCourseService = bilkentCourseService;
        this.userRepository = userRepository;
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
        coordinator1.setFirstName("asd");
        coordinator1.setFirstName("Can");
        coordinator1.setLastName("Alkan");
        coordinator1.setEmail("can.alkan@gmail.com");
        coordinator1.setDepartment(Department.CS);
        //password:"pass"
        coordinator1.setPassword("$2a$12$l1vmwtZF3nQ9zH152w850.BEbG/5yWyIljazxbSJZahCxNHZS3hBu");
        coordinator1.setBilkentId(1000);
        coordinator1.setRole(Role.ROLE_COORDINATOR);
        coordinatorList.add(coordinator1);

        Coordinator coordinator2 = new Coordinator();
        coordinator2.setFirstName("Ayşegül");
        coordinator2.setLastName("Dündar");
        coordinator2.setEmail("çiğdem.demir@gmail.com");
        coordinator2.setDepartment(Department.CS);
        //password:"pass"
        coordinator2.setPassword("$2a$12$l1vmwtZF3nQ9zH152w850.BEbG/5yWyIljazxbSJZahCxNHZS3hBu");
        coordinator2.setBilkentId(2000);
        coordinator2.setRole(Role.ROLE_COORDINATOR);
        coordinatorList.add(coordinator2);

        Iso iso = new Iso();
        iso.setBilkentId(3000);
        iso.setDepartment(Department.CS);
        iso.setFirstName("My");
        iso.setLastName("ISO");
        iso.setEmail("my.iso@example.com");
        iso.setPassword("$2a$12$l1vmwtZF3nQ9zH152w850.BEbG/5yWyIljazxbSJZahCxNHZS3hBu");
        iso.setRole(Role.ROLE_ISO);
        userRepository.save(iso);


//        coordinatorRepository.saveAll(coordinatorList);


        List<ErasmusUniversity> erasmusUniversities = new LinkedList<>();
        ErasmusUniversity erasmusUniversity1 = new ErasmusUniversity();
        erasmusUniversity1.setAllowance(400).setSemester(Semester.FALL).setName("Kingston University").setLanguageRequirement("English B2")
                .setCountry("United Kingdom").getCoordinators().add(coordinator1);
        Set<Program> programs = new HashSet<>();
        Program program = new Program().setDepartment(Department.CS).setQuota(4);
        programs.add(program);
        program.setUniversity(erasmusUniversity1);
        erasmusUniversity1.setPrograms(programs);
        coordinator1.getResponsibleSchools().add(erasmusUniversity1);

        erasmusUniversities.add(erasmusUniversity1);
        programRepository.save(program);

//
//        ErasmusUniversity erasmusUniversity2 = new ErasmusUniversity();
//        erasmusUniversity2.setAllowance(300).setSemester(Semester.BOTH).setName("TU Vienna").setLanguageRequirement("German B2")
//                .setCountry("Austria").getCoordinators().add(coordinator2);
//
//
//        programs = new HashSet<>();
//        program = new Program().setDepartment(Department.CS).setQuota(4);
//        programs.add(program);
//        erasmusUniversity2.setPrograms(programs);
//        program.setUniversity(erasmusUniversity2);
//        erasmusUniversities.add(erasmusUniversity2);
//        coordinator2.getResponsibleSchools().add(erasmusUniversity2);
//
//        programRepository.save(program);



        ErasmusUniversity erasmusUniversity3 = new ErasmusUniversity();
        erasmusUniversity3.setAllowance(250).setSemester(Semester.BOTH).setName("ESIEE Paris").setLanguageRequirement("France B2")
                .setCountry("France").getCoordinators().add(coordinator2);
        coordinator2.getResponsibleSchools().add(erasmusUniversity3);
        programs = new HashSet<>();
        program = new Program().setDepartment(Department.CS).setQuota(3);
        programs.add(program);
        program.setUniversity(erasmusUniversity3);
        erasmusUniversities.add(erasmusUniversity3);
        coordinator2.getResponsibleSchools().add(erasmusUniversity3);
        programRepository.save(program);


        ErasmusUniversity erasmusUniversity4 = new ErasmusUniversity();
        erasmusUniversity4.setAllowance(250).setSemester(Semester.BOTH).setName("ESIEA (Ecole Superieure d'Informatique, Electronique et Automatique)").setLanguageRequirement("English B2")
                .setCountry("France").getCoordinators().add(coordinator1);
        programs = new HashSet<>();
        program = new Program().setDepartment(Department.CS).setQuota(1);
        programs.add(program);
        program.setUniversity(erasmusUniversity4);
        erasmusUniversities.add(erasmusUniversity4);
        coordinator1.getResponsibleSchools().add(erasmusUniversity4);

        programRepository.save(program);

        ErasmusUniversity erasmusUniversity5 = new ErasmusUniversity();
        erasmusUniversity5.setAllowance(250).setSemester(Semester.BOTH).setName("Technical University of Berlin").setLanguageRequirement("German A2")
                .setCountry("Germany").getCoordinators().add(coordinator1);
        programs = new HashSet<>();
        program = new Program().setDepartment(Department.CS).setQuota(2);
        programs.add(program);
        program.setUniversity(erasmusUniversity5);
        erasmusUniversities.add(erasmusUniversity5);
        coordinator1.getResponsibleSchools().add(erasmusUniversity5);

        programRepository.save(program);


        ErasmusUniversity erasmusUniversity6 = new ErasmusUniversity();
        erasmusUniversity6.setAllowance(450).setSemester(Semester.BOTH).setName("Universita degli Studi di L'Aquila").setLanguageRequirement("English B1")
                .setCountry("Italy").getCoordinators().add(coordinator2);
        programs = new HashSet<>();
        program = new Program().setDepartment(Department.CS).setQuota(2);
        programs.add(program);
        program.setUniversity(erasmusUniversity6);
        erasmusUniversities.add(erasmusUniversity6);
        coordinator2.getResponsibleSchools().add(erasmusUniversity6);

        programRepository.save(program);

        ErasmusUniversity erasmusUniversity7 = new ErasmusUniversity();
        erasmusUniversity7.setAllowance(450).setSemester(Semester.BOTH).setName("AGH University of Science and Technology")
                .setLanguageRequirement("English B2")
                .setCountry("Poland").getCoordinators().add(coordinator2);
        programs = new HashSet<>();
        program = new Program().setDepartment(Department.CS).setQuota(2);
        programs.add(program);
        program.setUniversity(erasmusUniversity7);
        erasmusUniversities.add(erasmusUniversity7);
        coordinator2.getResponsibleSchools().add(erasmusUniversity7);

        programRepository.save(program);

        ErasmusUniversity erasmusUniversity8 = new ErasmusUniversity();
        erasmusUniversity8.setAllowance(350).setSemester(Semester.BOTH).setName("Roskilde University")
                .setLanguageRequirement("English B2")
                .setCountry("Denmark").getCoordinators().add(coordinator2);
        programs = new HashSet<>();
        program = new Program().setDepartment(Department.CS).setQuota(3);
        programs.add(program);
        program.setUniversity(erasmusUniversity8);
        erasmusUniversities.add(erasmusUniversity8);
        coordinator2.getResponsibleSchools().add(erasmusUniversity8);

        programRepository.save(program);

        ErasmusUniversity erasmusUniversity9 = new ErasmusUniversity();
        erasmusUniversity9.setAllowance(250).setSemester(Semester.BOTH)
                .setName("École Polytechnique Fédérale (EPF)")
                .setLanguageRequirement("French B2")
                .setCountry("France").getCoordinators().add(coordinator1);
        programs = new HashSet<>();
        program = new Program().setDepartment(Department.CS).setQuota(3);
        programs.add(program);
        program.setUniversity(erasmusUniversity9);
        erasmusUniversities.add(erasmusUniversity9);
        coordinator1.getResponsibleSchools().add(erasmusUniversity9);

        programRepository.save(program);

        ErasmusUniversity erasmusUniversity10 = new ErasmusUniversity();
        erasmusUniversity10.setAllowance(250).setSemester(Semester.BOTH)
                .setName("TU Universitaet Dortmund")
                .setLanguageRequirement("German B2")
                .setCountry("Germany").getCoordinators().add(coordinator2);
        programs = new HashSet<>();
        program = new Program().setDepartment(Department.CS).setQuota(3);
        programs.add(program);
        program.setUniversity(erasmusUniversity10);
        erasmusUniversities.add(erasmusUniversity10);
        coordinator2.getResponsibleSchools().add(erasmusUniversity10);

        programRepository.save(program);




        ErasmusUniversity erasmusUniversity11 = new ErasmusUniversity();
        erasmusUniversity11.setAllowance(250).setSemester(Semester.BOTH)
                .setName("Saarland University")
                .setLanguageRequirement("German B2")
                .setCountry("Germany").getCoordinators().add(coordinator2);
        programs = new HashSet<>();
        program = new Program().setDepartment(Department.CS).setQuota(3);
        programs.add(program);
        program.setUniversity(erasmusUniversity11);
        erasmusUniversities.add(erasmusUniversity11);
        coordinator2.getResponsibleSchools().add(erasmusUniversity11);

        programRepository.save(program);

        ErasmusUniversity erasmusUniversity12 = new ErasmusUniversity();
        erasmusUniversity12.setAllowance(250).setSemester(Semester.BOTH)
                .setName("Vrije University")
                .setLanguageRequirement("English B2")
                .setCountry("Germany").getCoordinators().add(coordinator2);
        programs = new HashSet<>();
        program = new Program().setDepartment(Department.CS).setQuota(3);
        programs.add(program);
        program.setUniversity(erasmusUniversity12);
        erasmusUniversities.add(erasmusUniversity12);
        coordinator2.getResponsibleSchools().add(erasmusUniversity12);

        programRepository.save(program);

        erasmusUniversityRepository.saveAll(erasmusUniversities);
        coordinatorRepository.saveAll(coordinatorList);


        ErasmusUniversity eras = erasmusUniversityRepository.findById(1L).get();
        System.out.println(eras.getCoordinators().size());

        Coordinator coordinator = coordinatorRepository.findByBilkentId(1000).get();


        System.out.println(coordinator.getResponsibleSchools().size());

        for(University university : coordinator.getResponsibleSchools()) {
            System.out.println(university);
        }
        getEPFLCourses();

    }


    void getEPFLCourses() {
        Set<ExternalCourse> courses = new HashSet<>();

        ExternalCourse course =  new ExternalCourse();
                course.setSyllabusLink("https://edu.epfl.ch/coursebook/en/software-engineering-CS-305").setCourseCode("CS305")
                .setEcts(5.0).setNormalCredit(4.0).setDepartment(Department.CS).setName("Software Engineering");
                course.setIsErasmus(true).setIsProject(false).setApprovalStatus(ApprovalStatus.ACCEPTED);
        course.setUniversity(erasmusUniversityRepository.findByName("École Polytechnique Fédérale (EPF)"));
        EquivalenceItem equivalenceItem = new EquivalenceItem();
        BilkentCourse bilkentCourse = bilkentCourseRepository.findCourseByCourseCode("CS319").get();
        equivalenceItem.setApprovalStatus(ApprovalStatus.ACCEPTED);
        equivalenceItem.setBilkentCourse(bilkentCourse);
        equivalenceItem.setExternalCourse(course);
        bilkentCourse.getExternalCourses().add(equivalenceItem);
        course.getEquivalentCourses().add(equivalenceItem);
        equivalenceItemRepository.save(equivalenceItem);

        course =  new ExternalCourse();
        course.setSyllabusLink("https://edu.epfl.ch/studyplan/en/bachelor/computer-science/coursebook/introduction-to-database-systems-CS-322").setCourseCode("CS322")
                .setEcts(5.0).setNormalCredit(4.0).setDepartment(Department.CS).setName("Introduction to Database Systems");
        course.setIsErasmus(true).setIsProject(false).setApprovalStatus(ApprovalStatus.ACCEPTED);
        course.setUniversity(erasmusUniversityRepository.findByName("École Polytechnique Fédérale (EPF)"));
        equivalenceItem = new EquivalenceItem();
        bilkentCourse = bilkentCourseRepository.findCourseByCourseCode("CS353").get();
        equivalenceItem.setApprovalStatus(ApprovalStatus.ACCEPTED);
        equivalenceItem.setBilkentCourse(bilkentCourse);
        equivalenceItem.setExternalCourse(course);
        bilkentCourse.getExternalCourses().add(equivalenceItem);
        course.getEquivalentCourses().add(equivalenceItem);
        equivalenceItemRepository.save(equivalenceItem);

        course =  new ExternalCourse();
        course.setSyllabusLink("https://edu.epfl.ch/studyplan/en/bachelor/computer-science/coursebook/introduction-to-operating-systems-CS-323").setCourseCode("CS323")
                .setEcts(5.0).setNormalCredit(4.0).setDepartment(Department.CS).setName("Introduction to operating systems");
        course.setIsErasmus(true).setIsProject(false).setApprovalStatus(ApprovalStatus.ACCEPTED);
        course.setUniversity(erasmusUniversityRepository.findByName("École Polytechnique Fédérale (EPF)"));
        equivalenceItem = new EquivalenceItem();
        bilkentCourse = bilkentCourseRepository.findCourseByCourseCode("CS342").get();
        equivalenceItem.setApprovalStatus(ApprovalStatus.ACCEPTED);
        equivalenceItem.setBilkentCourse(bilkentCourse);
        equivalenceItem.setExternalCourse(course);
        bilkentCourse.getExternalCourses().add(equivalenceItem);
        course.getEquivalentCourses().add(equivalenceItem);
        equivalenceItemRepository.save(equivalenceItem);

        course =  new ExternalCourse();
        course.setSyllabusLink("https://edu.epfl.ch/studyplan/en/bachelor/computer-science/coursebook/introduction-to-computer-graphics-CS-341").setCourseCode("CS341")
                .setEcts(5.0).setNormalCredit(4.0).setDepartment(Department.CS).setName("Introduction to computer graphics");
        course.setIsErasmus(true).setIsProject(false).setApprovalStatus(ApprovalStatus.ACCEPTED);
        course.setUniversity(erasmusUniversityRepository.findByName("École Polytechnique Fédérale (EPF)"));
        equivalenceItem = new EquivalenceItem();
        bilkentCourse = bilkentCourseRepository.findCourseByCourseCode("CS4xx").get();
        equivalenceItem.setApprovalStatus(ApprovalStatus.ACCEPTED);
        equivalenceItem.setBilkentCourse(bilkentCourse);
        equivalenceItem.setExternalCourse(course);
        bilkentCourse.getExternalCourses().add(equivalenceItem);
        course.getEquivalentCourses().add(equivalenceItem);
        equivalenceItemRepository.save(equivalenceItem);

        course =  new ExternalCourse();
        course.setSyllabusLink("https://edu.epfl.ch/studyplan/en/bachelor/computer-science/coursebook/algorithms-CS-250").setCourseCode("CS250")
                .setEcts(5.0).setNormalCredit(4.0).setDepartment(Department.CS).setName("Algorithms");
        course.setIsErasmus(true).setIsProject(false).setApprovalStatus(ApprovalStatus.ACCEPTED);
        course.setUniversity(erasmusUniversityRepository.findByName("École Polytechnique Fédérale (EPF)"));
        equivalenceItem = new EquivalenceItem();
        bilkentCourse = bilkentCourseRepository.findCourseByCourseCode("CS101").get();
        equivalenceItem.setApprovalStatus(ApprovalStatus.ACCEPTED);
        equivalenceItem.setBilkentCourse(bilkentCourse);
        equivalenceItem.setExternalCourse(course);
        bilkentCourse.getExternalCourses().add(equivalenceItem);
        course.getEquivalentCourses().add(equivalenceItem);
        equivalenceItemRepository.save(equivalenceItem);

        course =  new ExternalCourse();
        course.setSyllabusLink("https://edu.epfl.ch/studyplan/en/bachelor/computer-science/coursebook/quantum-computation-CS-308").setCourseCode("CS308")
                .setEcts(5.0).setNormalCredit(4.0).setDepartment(Department.CS).setName("Quantum computation");
        course.setIsErasmus(true).setIsProject(false).setApprovalStatus(ApprovalStatus.ACCEPTED);
        course.setUniversity(erasmusUniversityRepository.findByName("École Polytechnique Fédérale (EPF)"));
        equivalenceItem = new EquivalenceItem();
        bilkentCourse = bilkentCourseRepository.findCourseByCourseCode("CS4xx").get();
        equivalenceItem.setApprovalStatus(ApprovalStatus.ACCEPTED);
        equivalenceItem.setBilkentCourse(bilkentCourse);
        equivalenceItem.setExternalCourse(course);
        bilkentCourse.getExternalCourses().add(equivalenceItem);
        course.getEquivalentCourses().add(equivalenceItem);
        equivalenceItemRepository.save(equivalenceItem);

        course =  new ExternalCourse();
        course.setSyllabusLink("https://edu.epfl.ch/studyplan/en/bachelor/mathematics/coursebook/principles-of-microeconomics-MGT-454").setCourseCode("MGT454")
                .setEcts(5.0).setNormalCredit(4.0).setDepartment(Department.CS).setName("Principles of microeconomics");
        course.setIsErasmus(true).setIsProject(false).setApprovalStatus(ApprovalStatus.ACCEPTED);
        course.setUniversity(erasmusUniversityRepository.findByName("École Polytechnique Fédérale (EPF)"));
        equivalenceItem = new EquivalenceItem();
        bilkentCourse = bilkentCourseRepository.findCourseByCourseCode("CS4xx").get();
        equivalenceItem.setApprovalStatus(ApprovalStatus.DENIED);
        equivalenceItem.setBilkentCourse(bilkentCourse);
        equivalenceItem.setExternalCourse(course);
        bilkentCourse.getExternalCourses().add(equivalenceItem);
        course.getEquivalentCourses().add(equivalenceItem);
        equivalenceItemRepository.save(equivalenceItem);

        course =  new ExternalCourse();
        course.setSyllabusLink("https://edu.epfl.ch/studyplan/en/bachelor/computer-science/coursebook/computer-security-COM-301").setCourseCode("COM301")
                .setEcts(5.0).setNormalCredit(4.0).setDepartment(Department.CS).setName("Computer security");
        course.setIsErasmus(true).setIsProject(false).setApprovalStatus(ApprovalStatus.ACCEPTED);
        course.setUniversity(erasmusUniversityRepository.findByName("École Polytechnique Fédérale (EPF)"));
        equivalenceItem = new EquivalenceItem();
        bilkentCourse = bilkentCourseRepository.findCourseByCourseCode("CS4xx").get();
        equivalenceItem.setApprovalStatus(ApprovalStatus.PENDING);
        equivalenceItem.setBilkentCourse(bilkentCourse);
        equivalenceItem.setExternalCourse(course);
        bilkentCourse.getExternalCourses().add(equivalenceItem);
        course.getEquivalentCourses().add(equivalenceItem);
        equivalenceItemRepository.save(equivalenceItem);


    }


}
