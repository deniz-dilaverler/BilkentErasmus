package com.caddy.erasxchange.services.application;

import com.caddy.erasxchange.DTOs.ErasmusApplicationDto;
import com.caddy.erasxchange.DTOs.ErasmusApplicationPostDto;
import com.caddy.erasxchange.mappers.ErasmusApplicationMapper;
import com.caddy.erasxchange.models.Department;
import com.caddy.erasxchange.models.application.AppStatus;
import com.caddy.erasxchange.models.application.ErasmusApplication;
import com.caddy.erasxchange.models.application.ErasmusApplicationCancel;
import com.caddy.erasxchange.models.university.Program;
import com.caddy.erasxchange.models.users.Student;
import com.caddy.erasxchange.repositories.application.ErasmusApplicationCancelRepository;
import com.caddy.erasxchange.repositories.application.ErasmusApplicationRepository;

import java.util.List;

import com.caddy.erasxchange.repositories.user.StudentRepository;
import com.caddy.erasxchange.services.ApplicationStateService;
import com.caddy.erasxchange.services.application.applicationplacer.ErasmusApplicationPlacer;
import com.caddy.erasxchange.services.user.StudentService;
import com.sun.jdi.request.InvalidRequestStateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.Optional;


@Service
@Slf4j
public class ErasmusApplicationService extends ApplicationService<ErasmusApplication, ErasmusApplicationRepository> {

    private final ErasmusApplicationMapper erasmusMapper;
    private final ErasmusApplicationCancelRepository cancelRepository;
    @Autowired
    public ErasmusApplicationService(ErasmusApplicationRepository repository, StudentService studentService, ErasmusApplicationMapper erasmusMapper,
                                     ErasmusApplicationPlacer applicationPlacer,
                                     StudentRepository studentRepository,
                                     ErasmusApplicationRepository erasmusApplicationRepository, ErasmusApplicationCancelRepository cancelRepository,
                                     ApplicationStateService stateService) {
        super(repository, studentService, stateService, applicationPlacer);
        this.erasmusMapper = erasmusMapper;
        this.cancelRepository = cancelRepository;
    }

    @Override
    protected String getClassName() {
        return ErasmusApplication.class.getName();
    }

    public void addApplication(ErasmusApplicationPostDto erasmusApplicationPostDto) {
        if (!studentService.existsById(erasmusApplicationPostDto.getStudentId()))
            throw new EntityNotFoundException("Student with id doesn't");
        if (studentHasApplication(erasmusApplicationPostDto.getStudentId()))
            throw new EntityExistsException("Student with id -> " + erasmusApplicationPostDto.getStudentId() + " already has an Erasmus application");

        ErasmusApplication application = erasmusMapper.postToEntity(erasmusApplicationPostDto);

        application.getStudent().setErasmusApplication(application);

        repository.save(application);
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
        if(application.getStatus() == AppStatus.CANCELED) throw new UnsupportedOperationException("Can't cancel a canceled application");

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
                        , openedProgram.getDepartment() );
            }

        }

        if (cancelFully) {
            //detach from student
            Student student = application.getStudent();
            student.setErasmusApplication(null);
            application.setStudent(null);
            application.setStatus(AppStatus.CANCELED);
        } else {
            application.setStatus(AppStatus.WAITING_BIN);
        }



        //TODO: Generate event, according to wether the app was placed or not and fully canceled or not, send it to coordinator



        repository.save(application);
    }

    public void handleCancelation(Long cancelId, Boolean approveFromWaitingList) {
        Optional<ErasmusApplicationCancel> applicationCancel = cancelRepository.findById(cancelId);
        if (applicationCancel.isEmpty())
            throw new EntityNotFoundException("No ErasmusApplicationCancel with id: " + cancelId);

        ErasmusApplicationCancel cancel = applicationCancel.get();
        if(approveFromWaitingList) {
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
        if(stateService.erasmusAppsPlaced(department))
            throw new InvalidRequestStateException("Erasmus application for department :  " + department + " is already placed");

        List<ErasmusApplication> applications = new LinkedList<>();
        List<Student> students = studentService.findStudentsByDepartment(department);
        for (Student student : students) {
            ErasmusApplication erasmusApplication = student.getErasmusApplication();
            if (erasmusApplication != null) {
                applications.add(erasmusApplication);
            }
        }
        applicationPlacer.startPlacements(applications, department);

        stateService.setErasmusAppsPlaced(department);

    }

    private ErasmusApplication getHighestWaitingBin(Department department) {
        List<ErasmusApplication> waiting =  repository.findByStatusAndStudentDepartment(AppStatus.WAITING_BIN,department);
        if (waiting.size() <= 0)
            throw new EntityNotFoundException("There are no one in the waiting list for erasmus application department: " + department);
        ErasmusApplication highest = waiting.get(0);
        for(ErasmusApplication app : waiting) {
            if(highest.compareTo(app) < 0) highest = app;
        }

        return highest;
    }


}