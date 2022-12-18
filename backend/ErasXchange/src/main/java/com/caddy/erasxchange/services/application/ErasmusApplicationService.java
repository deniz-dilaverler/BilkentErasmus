package com.caddy.erasxchange.services.application;

import com.caddy.erasxchange.DTOs.ErasmusApplicationDto;
import com.caddy.erasxchange.mappers.ErasmusApplicationMapper;
import com.caddy.erasxchange.models.Department;
import com.caddy.erasxchange.models.Semester;
import com.caddy.erasxchange.models.application.AppStatus;
import com.caddy.erasxchange.models.application.ErasmusApplication;
import com.caddy.erasxchange.models.application.ErasmusApplicationCancel;
import com.caddy.erasxchange.models.university.ErasmusUniversity;
import com.caddy.erasxchange.models.university.Program;
import com.caddy.erasxchange.models.users.Student;
import com.caddy.erasxchange.repositories.application.ErasmusApplicationCancelRepository;
import com.caddy.erasxchange.repositories.application.ErasmusApplicationRepository;

import java.util.ArrayList;
import java.util.List;

import com.caddy.erasxchange.repositories.user.StudentRepository;
import com.caddy.erasxchange.services.ApplicationStateService;
import com.caddy.erasxchange.services.PlacementStatus;
import com.caddy.erasxchange.services.application.applicationplacer.ErasmusApplicationPlacer;
import com.caddy.erasxchange.services.university.ErasmusUniversityService;
import com.caddy.erasxchange.services.user.StudentService;
import com.sun.jdi.request.InvalidRequestStateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.Optional;


@Service
@Slf4j
public class ErasmusApplicationService extends ApplicationService<ErasmusApplication, ErasmusApplicationRepository> {

    private final ErasmusApplicationMapper erasmusMapper;
    private final ErasmusApplicationCancelRepository cancelRepository;
    private final ErasmusUniversityService uniService;

    @Autowired
    public ErasmusApplicationService(ErasmusApplicationRepository repository, StudentService studentService, ErasmusApplicationMapper erasmusMapper,
                                     ErasmusApplicationPlacer applicationPlacer,
                                     StudentRepository studentRepository,
                                     ErasmusApplicationRepository erasmusApplicationRepository, ErasmusApplicationCancelRepository cancelRepository,
                                     ApplicationStateService stateService, ErasmusUniversityService uniService) {
        super(repository, studentService, stateService, applicationPlacer);
        this.erasmusMapper = erasmusMapper;
        this.cancelRepository = cancelRepository;
        this.uniService = uniService;
    }

    @Override
    protected String getClassName() {
        return ErasmusApplication.class.getName();
    }

//    public void addApplication(ErasmusApplicationPostDto erasmusApplicationPostDto) {
//        if (!studentService.existsById(erasmusApplicationPostDto.getStudentId()))
//            throw new EntityNotFoundException("Student with id doesn't");
//        if (studentHasApplication(erasmusApplicationPostDto.getStudentId()))
//            throw new EntityExistsException("Student with id -> " + erasmusApplicationPostDto.getStudentId() + " already has an Erasmus application");
//
//        ErasmusApplication application = erasmusMapper.postToEntity(erasmusApplicationPostDto);
//
//        application.getStudent().setErasmusApplication(application);
//
//        repository.save(application);
//    }

    public List<ErasmusApplicationDto> getAll() {
        return erasmusMapper.toDtoList(repository.findAll());
    }

    public boolean studentHasApplication(Long studentId) {
        Student student = studentService.findById(studentId);
        return student.getErasmusApplication() != null;
    }

    public ErasmusApplicationDto getApplication(Long id) {
        return erasmusMapper.toDto(super.findById(id));
    }

    public ErasmusApplicationDto getApplicationByBilkentId(Integer bilkentId) {
        return erasmusMapper.toDto(studentService.findByBilkentId(bilkentId).getErasmusApplication());
    }


    //cancelFully flag deletes application if true, puts the application into waiting list if false
    public void cancelApplication(Long id, Boolean cancelFully) {
        ErasmusApplication application = findById(id);
        if (application.getStatus() == AppStatus.CANCELED)
            throw new UnsupportedOperationException("Can't cancel a canceled application");

        if ((application.getStatus() == AppStatus.PLACED)) {
            // find program in school matching to the applying students department
            Program openedProgram = application.getPlacedSchool().getPrograms().stream()
                    .filter(program -> program.getDepartment() == application.getStudent().getDepartment())
                    .findFirst()
                    .orElse(null);

            //TODO: SEND NOTİF TO THE COORDİNATOR FOR THE OPENİNG

            application.setPlacedSchoolToNull();

            try {
                ErasmusApplicationCancel cancel = new ErasmusApplicationCancel();
                cancel.setCanceledApplication(application);
                cancel.setProposedProgram(openedProgram);
                cancel.setProposedApplication(getHighestWaitingBin(openedProgram.getDepartment()));
                cancelRepository.save(cancel);
            } catch (EntityNotFoundException e) {
                log.warn("ErasmusApplicationCancel object couldn't be formed since there are no reasmus applications in the waiting bin for the {} department"
                        , openedProgram.getDepartment());
            }

        }

        if (cancelFully) {
            //detach from student
            application.setStatus(AppStatus.CANCELED);
        } else {
            application.setStatus(AppStatus.WAITING_BIN);
        }


        //TODO: Generate event, according to wether the app was placed or not and fully canceled or not, send it to coordinator


        repository.save(application);
    }

    public AppStatus getStudentAppStatus(Long studentId) {
        Student student = studentService.findById(studentId);
        return student.getErasmusApplication().getStatus();
    }

    public void handleCancelation(Long cancelId, Boolean approveFromWaitingList) {
        Optional<ErasmusApplicationCancel> applicationCancel = cancelRepository.findById(cancelId);
        if (applicationCancel.isEmpty())
            throw new EntityNotFoundException("No ErasmusApplicationCancel with id: " + cancelId);

        ErasmusApplicationCancel cancel = applicationCancel.get();
        if (approveFromWaitingList) {
            ErasmusApplication erasmusApplication = cancel.getProposedApplication();
            erasmusApplication.setPlacedSchool(cancel.getProposedProgram().getUniversity());
            erasmusApplication.setStatus(AppStatus.PLACED);
            //TODO: notify the student about placement
            repository.save(erasmusApplication);
        }

        cancelRepository.delete(cancel);
    }


    @Transactional
    public void startPlacements(Department department) {

        if (stateService.getErasmusPlacementState(department) == PlacementStatus.PUBLISHED)
            throw new InvalidRequestStateException("Erasmus application for department :  " + department + " is already placed");

        List<ErasmusApplication> applications = repository.findByStatusAndStudentDepartment(AppStatus.PENDING, Department.CS);
        
        applicationPlacer.startPlacements(applications, department);

//        stateService.setErasmusAppsPlaced(department);
        stateService.setErasmusAppState(department, PlacementStatus.PUBLISHED);

    }


    public void changeSemester(Long appId, Integer choiceNo) {
        if (choiceNo > 5 || choiceNo < 1)
            throw new InvalidRequestStateException("choices can only be 1 to 5");

        ErasmusApplication app = super.findById(appId);

        switch (choiceNo) {
            case 1:
                if (app.getChoice1() == null) {
                    throw new EntityNotFoundException("No choice 1 for application with id " + appId);
                }
                if (app.getChoice1().getSemester() == Semester.BOTH) break;

                app.setSemester1(app.getChoice1().getSemester());
                break;
            case 2:
                if (app.getChoice2() == null) {
                    throw new EntityNotFoundException("No choice 2 for application with id " + appId);
                }
                if (app.getChoice2().getSemester() == Semester.BOTH) break;

                app.setSemester2(app.getChoice2().getSemester());
                break;
            case 3:
                if (app.getChoice1() == null) {
                    throw new EntityNotFoundException("No choice 3 for application with id " + appId);
                }
                if (app.getChoice3().getSemester() == Semester.BOTH) break;

                app.setSemester3(app.getChoice3().getSemester());
                break;
            case 4:
                if (app.getChoice1() == null) {
                    throw new EntityNotFoundException("No choice 4 for application with id " + appId);
                }
                if (app.getChoice4().getSemester() == Semester.BOTH) break;

                app.setSemester4(app.getChoice4().getSemester());
                break;
            case 5:
                if (app.getChoice1() == null) {
                    throw new EntityNotFoundException("No choice 5 for application with id " + appId);
                }
                if (app.getChoice5().getSemester() == Semester.BOTH) break;

                app.setSemester5(app.getChoice5().getSemester());
                break;
        }
        repository.save(app);
        checkApplications(app.getStudent().getDepartment());

    }

    public void cancelChoice(Long appId, Integer choiceNo) {
        if (choiceNo > 5 || choiceNo < 1)
            throw new InvalidRequestStateException("choiceNo can only be 1 to 5");
        ErasmusApplication app = super.findById(appId);

        switch (choiceNo) {
            case 1:
                if (app.getChoice1() == null) {
                    throw new EntityNotFoundException("No choice 1 for application with id " + appId);
                }
                app.setChoice1(null);
                app.setSemester1(null);
                break;
            case 2:
                if (app.getChoice2() == null) {
                    throw new EntityNotFoundException("No choice 2 for application with id " + appId);
                }
                app.setChoice2(null);
                app.setSemester2(null);
                break;
            case 3:
                if (app.getChoice1() == null) {
                    throw new EntityNotFoundException("No choice 3 for application with id " + appId);
                }
                app.setChoice3(null);
                app.setSemester3(null);
                break;
            case 4:
                if (app.getChoice1() == null) {
                    throw new EntityNotFoundException("No choice 4 for application with id " + appId);
                }
                app.setChoice4(null);
                app.setSemester4(null);
                break;
            case 5:
                if (app.getChoice1() == null) {
                    throw new EntityNotFoundException("No choice 5 for application with id " + appId);
                }
                app.setChoice5(null);
                app.setSemester5(null);
                break;
        }
        repository.save(app);
        checkApplications(app.getStudent().getDepartment());


    }

    private ErasmusApplication getHighestWaitingBin(Department department) {
        List<ErasmusApplication> waiting = repository.findByStatusAndStudentDepartment(AppStatus.WAITING_BIN, department);
        if (waiting.size() == 0)
            throw new EntityNotFoundException("There are no one in the waiting list for erasmus application department: " + department);
        ErasmusApplication highest = waiting.get(0);
        for (ErasmusApplication app : waiting) {
            if (highest.compareTo(app) < 0) highest = app;
        }

        return highest;
    }

    public  Boolean[] getSemesterCorrect(Long appId) {
        Boolean[] correctSemester = new Boolean[5];

        ErasmusApplication app = super.findById(appId);

        correctSemester[0] = checkSemester(app.getChoice1(), app.getSemester1());
        correctSemester[1] = checkSemester(app.getChoice2(), app.getSemester2());
        correctSemester[2] = checkSemester(app.getChoice3(), app.getSemester3());
        correctSemester[3] = checkSemester(app.getChoice4(), app.getSemester4());
        correctSemester[4] = checkSemester(app.getChoice5(), app.getSemester5());


        return correctSemester;


    }

    private  Boolean checkSemester(ErasmusUniversity choice, Semester semester ) {
        if(choice == null)
            return null;
        else if (choice.getSemester() == Semester.BOTH || choice.getSemester() == semester)
            return  true;
        else
            return false;
    }


    public void checkApplications(Department department) {
        PlacementStatus placementStatus = stateService.getErasmusPlacementState(department);
        if (placementStatus == PlacementStatus.APPS_CORRECT ||
                placementStatus == PlacementStatus.PUBLISHED) {
            return;
        } else if (placementStatus == PlacementStatus.APPS_CREATED ||
                    placementStatus == PlacementStatus.ACTIVATED) {

            List<ErasmusApplication> wrongApps = new ArrayList<>();
            List<ErasmusApplication> appsToCheck =  repository.findByStatusAndStudentDepartment(AppStatus.PENDING, department);

            for(ErasmusApplication app : appsToCheck) {
                boolean isCorrect = true;

                Boolean check = checkSemester(app.getChoice1(),app.getSemester1() );
                //check is not null and false
                if (check != null && !check ) {
                    isCorrect = false;
                }

                check = checkSemester(app.getChoice2(),app.getSemester2() );
                //check is not null and false
                if (check != null && !check ) {
                    isCorrect = false;
                }


                check = checkSemester(app.getChoice3(),app.getSemester3() );
                //check is not null and false
                if (check != null && !check ) {
                    isCorrect = false;
                }

                check = checkSemester(app.getChoice4(),app.getSemester4() );
                //check is not null and false
                if (check != null && !check ) {
                    isCorrect = false;
                }

                check = checkSemester(app.getChoice5(),app.getSemester5() );
                //check is not null and false
                if (check != null && !check ) {
                    isCorrect = false;
                }

                if(!isCorrect) {
                    wrongApps.add(app);
                }

            }
            stateService.setErasmusAppState(department,PlacementStatus.ACTIVATED);
            if (wrongApps.size() == 0) {

                stateService.setErasmusAppState(department, PlacementStatus.APPS_CORRECT);
            } else {
                //TODO: send wrong apss to coord

            }

        }
    }
}
