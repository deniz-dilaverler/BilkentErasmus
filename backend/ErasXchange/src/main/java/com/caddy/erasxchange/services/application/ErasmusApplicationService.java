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
/**
 * ErasmusApplicationService handles all requests about erasmus applications
 * extends ApplicationService for general requests on applications, which extends GenericService for
 */
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

    /**
     * @return list of all applications in the system
     */
    public List<ErasmusApplicationDto> getAll() {
        return erasmusMapper.toDtoList(repository.findAll());
    }

    /**
     * @param studentId
     * @return whether the student has an erasmusApplication
     */
    public boolean studentHasApplication(Long studentId) {
        Student student = studentService.findById(studentId);
        return student.getErasmusApplication() != null;
    }

    /**
     * @param id : id of the application in the database
     * @return the application in DTO form
     */
    public ErasmusApplicationDto getApplication(Long id) {
        return erasmusMapper.toDto(super.findById(id));
    }

    /**
     * @param bilkentId of the studemt
     * @return returns application in dto form
     */
    public ErasmusApplicationDto getApplicationByBilkentId(Integer bilkentId) {
        return erasmusMapper.toDto(studentService.findByBilkentId(bilkentId).getErasmusApplication());
    }



    /**
     * cancels application from the given id
     * @param id
     * @param cancelFully flag application puts app into canceled state if true, puts the application into waiting_bin stae
     *                   if false
     */
    public void cancelApplication(Long id, Boolean cancelFully) {
        ErasmusApplication application = findById(id);
        // don't cancel twice
        if (application.getStatus() == AppStatus.CANCELED)
            throw new UnsupportedOperationException("Can't cancel a canceled application");

        if ((application.getStatus() == AppStatus.PLACED)) {
            // find program in application's placed school whose department is  matching  the applying student's department
            Program openedProgram = application.getPlacedSchool().getPrograms().stream()
                    .filter(program -> program.getDepartment() == application.getStudent().getDepartment())
                    .findFirst()
                    .orElse(null);

            //TODO: SEND NOTİF TO THE COORDİNATOR FOR THE OPENİNG

            application.setPlacedSchoolToNull();
            // generate application cancel object, this will be handled by the coordinator with another request,
            // this object stores the proposed application, canceled application and the proposed program that the previous
            // applicant canceled
            try {
                ErasmusApplicationCancel cancel = new ErasmusApplicationCancel();
                cancel.setCanceledApplication(application);
                cancel.setProposedProgram(openedProgram);
                cancel.setProposedApplication(getHighestWaitingBin(openedProgram.getDepartment()));
                cancelRepository.save(cancel);
            } catch (EntityNotFoundException e) {
                log.warn("ErasmusApplicationCancel object couldn't be formed since there are no erasmus applications in the waiting bin for the {} department"
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
        checkApplications(application.getDepartment());
    }

    /**
     * @param studentId
     * @return status of the erasmus application of given student
     */
    public AppStatus getStudentAppStatus(Long studentId) {
        Student student = studentService.findById(studentId);
        return student.getErasmusApplication().getStatus();
    }

    /**
     * handles the ErasmusApplicationCancel object, coordinator chooses whether to place the student to whether
     * place the proposed application to  the proposed program or don't place anyone
     * @param cancelId id of the ErasmusApplicationCancel entity
     * @param approveFromWaitingList flag for if the proposed applications should be placed to the program or not
     */
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

    /**
     * starts application placement of a given department
     * @param department
     */
    @Transactional
    public void startPlacements(Department department) {

        if (stateService.getErasmusPlacementState(department) == PlacementStatus.PUBLISHED)
            throw new InvalidRequestStateException("Erasmus application for department :  " + department + " is already placed");

        if(stateService.getErasmusPlacementState(department ) != PlacementStatus.APPS_CORRECT)
            throw  new InvalidRequestStateException("There are incorrect applications");

        List<ErasmusApplication> applications = repository.findByStatusAndStudentDepartment(AppStatus.PENDING, Department.CS);

        applicationPlacer.startPlacements(applications, department);

        // adjust placement state for department
        stateService.setErasmusAppState(department, PlacementStatus.PUBLISHED);

    }


    /**
     * change semester of a single university choice of an application
     * changes it to the Universities supported semester
     * @param appId id of application
     * @param choiceNo which choice is the request proposing to change (must be in [1,5])
     */
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

    /**
     * cancels  a single choice university  choice of an application
     * nullifies the canceled choice and the semester of the said choice
     * @param appId id of application
     * @param choiceNo which choice is the request proposing to change (must be in [1,5])     */
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

    /**
     * @param department
     * @return application whose owner has the highest exchange score and in the waiting bin
     */
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

    /**
     * @param appId id of application
     * @return an array of Booleans. If choice's semester is correct the field is true, if the choice's semester is not applicable
     *          to the proposed school field is false, if there is no choice in that index, field is null
     */
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

    /**
     * @param choice the school whose applicability to the given semester will be checked
     * @param semester proposed semester
     * @return if choice's semester is correct true, if the choice's semester is not applicable
     *               to the proposed school return  false, if there is no choice  in the passed field return null
     *
     */
    private  Boolean checkSemester(ErasmusUniversity choice, Semester semester ) {
        if(choice == null)
            return null;
        else if (choice.getSemester() == Semester.BOTH || choice.getSemester() == semester)
            return  true;
        else
            return false;
    }


    /**
     * checks if applications of a given department are correct, if all of them are correct, (semester applicability wise)
     * department goes to Placement.APPS_CORRECT state, if there is at least a single problematic application remain at or go to
     * Placement.ACTIVATED state
     * @param department
     */
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

            if (wrongApps.size() == 0) {

                stateService.setErasmusAppState(department, PlacementStatus.APPS_CORRECT);
            } else {
                stateService.setErasmusAppState(department,PlacementStatus.ACTIVATED);

            }

        }
    }
}
