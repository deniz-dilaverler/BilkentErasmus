package com.caddy.erasxchange.services.application;

import com.caddy.erasxchange.DTOs.ApplicationDto;
import com.caddy.erasxchange.DTOs.ApplicationPostDto;
import com.caddy.erasxchange.mappers.ApplicationMapper;
import com.caddy.erasxchange.mappers.ApplicationPostMapper;
import com.caddy.erasxchange.models.application.AppStatus;
import com.caddy.erasxchange.models.application.Application;
import com.caddy.erasxchange.models.users.Student;
import com.caddy.erasxchange.repositories.application.ApplicationRepository;
import com.caddy.erasxchange.services.GenericService;
import com.caddy.erasxchange.services.user.StudentService;
import lombok.extern.slf4j.Slf4j;


import java.util.List;

@Slf4j
public abstract class ApplicationServiceStrategy <App extends Application, Repository extends ApplicationRepository<App>>
        extends GenericService<App, Repository> {

    final protected ApplicationMapper applicationMapper;

    final protected ApplicationPostMapper applicationPostMapper;
    final protected StudentService studentService;

    public ApplicationServiceStrategy(Repository repository, ApplicationMapper applicationMapper, ApplicationPostMapper applicationPostMapper, StudentService studentService) {
        super(repository);
        this.applicationMapper = applicationMapper;
        this.applicationPostMapper = applicationPostMapper;
        this.studentService = studentService;
    }

    public void addApplication(ApplicationPostDto applicationPostDto) {
        if(checkStudentHasApplication(applicationPostDto.getStudentId())) {
            log.info("Application can't be added to student with id: {} since the student already has an id ", applicationPostDto.getStudentId() );
            return;
        }
        App application  = postDTOtoEntity(applicationPostDto);
        setApplicationToStudent(application);

        repository.save(application);
    }
    public  ApplicationDto getById(Long id) {
        App application =  findById(id);
        return applicationMapper.toDto(application);
    }
    public void updateApp(ApplicationDto applicationDto) {
        if (!repository.existsById(applicationDto.getId())) {
            log.info("No application with the id : {} to be updated", applicationDto.getId());
            return;
        }

        App appToUpdate = findById(applicationDto.getId());
        update(applicationDto, appToUpdate);
        repository.save(appToUpdate);
    }
    public List<ApplicationDto> getAll() {
        // since App extends Application, no need to check type casting
        return applicationMapper.toDtoList((List<Application>) findAll());
    }
    public void cancelApplication(Long id, boolean cancelAll) {

    }
    public List<ApplicationDto> getApplicationWithStatus(AppStatus appStatus) {
        // since App extends Application, no need to check type casting
        return applicationMapper.toDtoList((List<Application>) repository.findByStatus(appStatus));
    }

    public boolean checkDoubleApplication(Long studentId) {
        Student student = studentService.findById(studentId);

        return (student.getErasmusApplication() != null) && (student.getBilateralApplication() != null);
    }

    /*
    * Generic Types don't allow object initialisation so the children will implement object construction
    * */

    protected abstract App generate();
    protected abstract App toEntity(ApplicationDto applicationDto);

    protected abstract App postDTOtoEntity(ApplicationPostDto applicationPostDto);
    protected abstract void setApplicationToStudent(App application);
    protected abstract void update(ApplicationDto applicationDto, App application);
    protected  abstract boolean checkStudentHasApplication(Long id);
}
