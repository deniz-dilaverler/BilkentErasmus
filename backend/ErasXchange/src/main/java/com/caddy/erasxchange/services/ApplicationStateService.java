package com.caddy.erasxchange.services;

import com.caddy.erasxchange.models.ApplicationState;
import com.caddy.erasxchange.models.Department;
import com.caddy.erasxchange.repositories.ApplicationStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * This service handles the application state information. The server should have one ApplicationState
 * Entity at all times. If an entity doesn't exist it should be initialised
 */




@Service
public class ApplicationStateService {

    private Map<Department, PlacementStatus> erasmusAppState;
    private final ApplicationStateRepository repository;


    @Autowired
    public ApplicationStateService(ApplicationStateRepository repository) {
        this.repository = repository;
       // initState();
    }

    private void initState() {

        erasmusAppState = new HashMap<>();
        for(Department department:Department.values()) {
            erasmusAppState.put(department, PlacementStatus.NO_FILE);
        }




//        ApplicationState applicationState = null;
//        if (repository.count() == 0) {
//            applicationState = new ApplicationState();
//        } else {
//            // there must be at least one state so no need for checking
//            applicationState = getState();
//
//        }
//
//        for (Department department : Department.values()) {
//            applicationState.getBilateralAppsPlaced().putIfAbsent(department, false);
//            applicationState.getErasmusAppsPlaced().putIfAbsent(department, false);
//
//        }
//        repository.saveAndFlush(applicationState);
//
//        ApplicationState state = getState();
//        System.out.println(state.getBilateralAppsPlaced().size());

    }

    public PlacementStatus getErasmusPlacementState(Department department) {
        return erasmusAppState.get(department);
    }

    public void setErasmusAppState(Department department, PlacementStatus state) {
        erasmusAppState.put(department, state);
    }




}
