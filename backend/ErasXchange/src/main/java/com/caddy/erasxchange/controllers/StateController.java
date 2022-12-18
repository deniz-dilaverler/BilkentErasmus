package com.caddy.erasxchange.controllers;


import com.caddy.erasxchange.models.Department;
import com.caddy.erasxchange.services.ApplicationStateService;
import com.caddy.erasxchange.services.PlacementStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/state")
public class StateController {
    private final ApplicationStateService  stateService;

    @Autowired
    public StateController(ApplicationStateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping("/placement/erasmus/{department}")
    public ResponseEntity<PlacementStatus> getErasmusPlacementState(@PathVariable Department department) {

        return new ResponseEntity<>(stateService.getErasmusPlacementState(department), HttpStatus.OK);
    }

}
