package com.caddy.erasxchange.services.application;

import com.caddy.erasxchange.DTOs.ErasmusApplicationDto;
import com.caddy.erasxchange.DTOs.ErasmusApplicationPostDto;
import com.caddy.erasxchange.mappers.ErasmusApplicationMapper;
import com.caddy.erasxchange.models.Department;
import com.caddy.erasxchange.models.application.AppStatus;
import com.caddy.erasxchange.models.application.ErasmusApplication;
import com.caddy.erasxchange.models.university.Program;
import com.caddy.erasxchange.models.users.Student;
import com.caddy.erasxchange.repositories.application.ErasmusApplicationRepository;

import java.util.List;

import com.caddy.erasxchange.repositories.user.StudentRepository;
import com.caddy.erasxchange.services.application.applicationplacer.ErasmusApplicationPlacer;
import com.caddy.erasxchange.services.user.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.LinkedList;
import java.util.List;


@Service
public class ErasmusApplicationService extends ApplicationService<ErasmusApplication, ErasmusApplicationRepository> {

    private final ErasmusApplicationMapper erasmusMapper;

    @Autowired
    public ErasmusApplicationService(ErasmusApplicationRepository repository, StudentService studentService, ErasmusApplicationMapper erasmusMapper,
                                         ErasmusApplicationPlacer applicationPlacer,
StudentRepository studentRepository) {
        super(repository, studentService,applicationPlacer );
        this.erasmusMapper = erasmusMapper;
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
        if ((application.getStatus() == AppStatus.PLACED)) {
            // find program in school matching to the applied department
            Program openedProgram = application.getPlacedSchool().getPrograms().stream()
                    .filter(program -> program.getDepartment() == application.getStudent().getDepartment())
                    .findFirst()
                    .orElse(null);

            //TODO: SEND NOTİF TO THE COORDİNATOR FOR THE OPENİNG
            application.setPlacedSchoolToNull();
        }
        if (cancelFully) {
            //detach from student
            Student student = application.getStudent();
            student.setErasmusApplication(null);
            application.setStudent(null);
            repository.delete(application);
        } else {
            application.setStatus(AppStatus.WAITING_BIN);
        }

        //TODO: Generate event, according to wether the app was placed or not and fully canceled or not, send it to coordinator
    }

    public void startPlacements(Department department) {
        //TODO: Check from app state wetehr placements have taken place already
        List<ErasmusApplication> applications = new LinkedList<>();
        List<Student> students = studentService.findStudentsByDepartment(department);
        for (Student student : students) {
            ErasmusApplication erasmusApplication = student.getErasmusApplication();
            if(erasmusApplication != null) {
                applications.add(erasmusApplication);
            }
         }
        applicationPlacer.startPlacements(applications, department);

    }


}
