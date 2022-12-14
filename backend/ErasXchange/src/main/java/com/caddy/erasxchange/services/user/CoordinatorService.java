package com.caddy.erasxchange.services.user;

import com.caddy.erasxchange.models.users.Coordinator;
import com.caddy.erasxchange.models.users.Student;
import com.caddy.erasxchange.repositories.user.CoordinatorRepository;
import com.caddy.erasxchange.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.Optional;

@Service
public class CoordinatorService extends GenericService<Coordinator, CoordinatorRepository> {


    @Autowired
    public CoordinatorService(CoordinatorRepository repository) {
        super(repository);
    }

    @Override
    protected String getClassName() {
        return Coordinator.class.getName();
    }


}
