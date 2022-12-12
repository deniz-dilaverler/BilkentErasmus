package com.caddy.erasxchange.services.application;

import com.caddy.erasxchange.DTOs.ApplicationDto;
import com.caddy.erasxchange.DTOs.ApplicationPostDto;
import com.caddy.erasxchange.mappers.ApplicationMapper;
import com.caddy.erasxchange.mappers.ApplicationPostMapper;
import com.caddy.erasxchange.models.application.ErasmusApplication;
import com.caddy.erasxchange.models.users.Student;
import com.caddy.erasxchange.repositories.application.ErasmusApplicationRepository;
import com.caddy.erasxchange.services.user.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ErasmusApplicationService extends  ApplicationServiceStrategy<ErasmusApplication, ErasmusApplicationRepository> {


    @Autowired
    public ErasmusApplicationService(ErasmusApplicationRepository repository, ApplicationMapper applicationMapper,
                                     ApplicationPostMapper applicationPostMapper, StudentService studentService) {
        super(repository,
                applicationMapper,
                applicationPostMapper,
                studentService);
    }

    @Override
    protected ErasmusApplication generate() {
        return new ErasmusApplication();
    }

    @Override
    protected ErasmusApplication toEntity(ApplicationDto applicationDto) {
        return applicationMapper.toErasmusApp(applicationDto);
    }



    @Override
    protected ErasmusApplication postDTOtoEntity(ApplicationPostDto applicationPostDto) {
        return applicationPostMapper.applicationPostToErasmusApplicationMapper(applicationPostDto);
    }

    @Override
    protected void setApplicationToStudent(ErasmusApplication application) {
        application.getStudent().setErasmusApplication(application);
    }

    @Override
    protected void update(ApplicationDto applicationDto, ErasmusApplication application) {
        ErasmusApplication updated = applicationMapper.updateErasmusApplication(applicationDto, application);
        repository.save(updated);
    }

    @Override
    protected boolean checkStudentHasApplication(Long id) {
        Student student = studentService.findById(id);

        return student.getErasmusApplication() != null  ;
    }
}
