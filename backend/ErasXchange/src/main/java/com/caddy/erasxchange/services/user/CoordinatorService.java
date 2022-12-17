package com.caddy.erasxchange.services.user;

import com.caddy.erasxchange.models.users.Coordinator;
import com.caddy.erasxchange.repositories.user.CoordinatorRepository;
import com.caddy.erasxchange.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoordinatorService extends GenericService<Coordinator, CoordinatorRepository> {


    public CoordinatorService(CoordinatorRepository coordinatorRepository) {
        super(coordinatorRepository);
    }

    @Override
    protected String getClassName() {
        return Coordinator.class.getName();
    }



}
