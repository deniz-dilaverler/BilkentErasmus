package com.caddy.erasxchange.services.application;

import com.caddy.erasxchange.models.application.Application;
import com.caddy.erasxchange.repositories.application.ApplicationRepository;
import com.caddy.erasxchange.services.GenericService;
import com.caddy.erasxchange.services.application.applicationplacer.ApplicationPlacementStrategy;
import com.caddy.erasxchange.services.user.StudentService;

public abstract class ApplicationService<App extends Application, Repository extends ApplicationRepository<App>> extends
        GenericService<App, ApplicationRepository<App>> {
    final protected StudentService studentService;
    final protected ApplicationPlacementStrategy applicationPlacer;
    public ApplicationService(Repository repository, StudentService studentService, ApplicationPlacementStrategy applicationPlacer) {
        super(repository);
        this.studentService = studentService;
        this.applicationPlacer = applicationPlacer;
    }





    public abstract  boolean studentHasApplication(Long studentId);

}
