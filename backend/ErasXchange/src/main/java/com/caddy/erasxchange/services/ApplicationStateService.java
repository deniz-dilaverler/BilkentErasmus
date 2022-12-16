package com.caddy.erasxchange.services;

import com.caddy.erasxchange.models.ApplicationState;
import com.caddy.erasxchange.models.Department;
import com.caddy.erasxchange.repositories.ApplicationStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This service handles the application state information. The server should have one ApplicationState
 * Entity at all times. If an entity doesn't exist it should be initialised
 */
@Service
public class ApplicationStateService {

    private final ApplicationStateRepository repository;

    @Autowired
    public ApplicationStateService(ApplicationStateRepository repository) {
        this.repository = repository;
        initState();
    }

    private void initState() {
        ApplicationState applicationState = null;
        if (repository.count() == 0) {
            applicationState = new ApplicationState();
        } else {
            // there must be at least one state so no need for checking
            applicationState = getState();

        }

        for (Department department : Department.values()) {
            applicationState.getBilateralAppsPlaced().putIfAbsent(department, false);
            applicationState.getErasmusAppsPlaced().putIfAbsent(department, false);

        }
        repository.saveAndFlush(applicationState);
        applicationState = null;

        applicationState = getState();
        System.out.println(applicationState.getBilateralAppsPlaced().size());
    }

    public boolean erasmusAppsPlaced(Department department) {
        return getState().getErasmusAppsPlaced().get(department);
    }

    public boolean bilateralAppsPlaced(Department department) {
        return getState().getBilateralAppsPlaced().get(department);
    }

    public void setErasmusAppsPlaced(Department department) {
        ApplicationState state = getState();
        state.getErasmusAppsPlaced().put(department, true);
        repository.save(state);
    }

    public void setBilateralAppsPlaced(Department department) {
        ApplicationState state = getState();
        state.getBilateralAppsPlaced().put(department, true);
        repository.save(state);
    }

    private ApplicationState getState() {
        // there must be at least one state so no need for checking
        return repository.findAll().get(0);
    }
}
