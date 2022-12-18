package com.caddy.erasxchange.controllers.application;


import com.caddy.erasxchange.DTOs.ErasmusApplicationDto;
import com.caddy.erasxchange.models.Department;
import com.caddy.erasxchange.models.application.AppStatus;
import com.caddy.erasxchange.services.application.ErasmusApplicationService;
import com.github.javafaker.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/application/erasmus")
public class ErasmusApplicationController {
    private final ErasmusApplicationService erasmusApplicationService;

    @Autowired
    public ErasmusApplicationController(ErasmusApplicationService erasmusApplicationService) {
        this.erasmusApplicationService = erasmusApplicationService;
    }

    @PutMapping("/placement/{department}")
    public ResponseEntity startPlacements(@PathVariable Department department) {
        erasmusApplicationService.startPlacements(department);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ErasmusApplicationDto> getApplication(@PathVariable Long id) {
        ErasmusApplicationDto app =  erasmusApplicationService.getApplication(id);

        return new ResponseEntity<ErasmusApplicationDto>(app,
                                                HttpStatus.OK);
    }

    @GetMapping("/bybilkentid/{bilkentId}")
    public ResponseEntity<ErasmusApplicationDto> getApplicationByBilkentId(@PathVariable Integer bilkentId) {
        ErasmusApplicationDto app =  erasmusApplicationService.getApplicationByBilkentId(bilkentId);

        return new ResponseEntity<ErasmusApplicationDto>(app,
                HttpStatus.OK);
    }

    @DeleteMapping("/{appId}/{cancelAll}")
    public ResponseEntity cancelApplication(@PathVariable Long appId, @PathVariable  boolean  cancelAll) {
        erasmusApplicationService.cancelApplication(appId,cancelAll );

        return new ResponseEntity(HttpStatus.OK);
    }


    @PutMapping("/cancellation/handle/{cancelId}/{approveNewApp}")
    public HttpStatus handleCancellation(@PathVariable  Long cancelId, @PathVariable Boolean approveNewApp) {
        erasmusApplicationService.handleCancelation(cancelId, approveNewApp);

        return HttpStatus.OK;
    }


    @GetMapping("/all")
    public ResponseEntity<List<ErasmusApplicationDto>> getApplications() {
        List<ErasmusApplicationDto> apps = erasmusApplicationService.getAll();
        return new ResponseEntity<>( apps, HttpStatus.OK) ;

    }

    @GetMapping("/status/{studentId}")
    public ResponseEntity<AppStatus> getApplicationStatus(@PathVariable Long studentId) {
        AppStatus status = erasmusApplicationService.getStudentAppStatus(studentId);

        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @GetMapping("/wrongsemester/{applicationId}")
    public ResponseEntity<Boolean[]> getCorrectSemesters(@PathVariable Long applicationId) {
        Boolean[] correctness = erasmusApplicationService.getSemesterCorrect(applicationId);

        return new ResponseEntity<>(correctness, HttpStatus.OK);
    }

    @PutMapping("/change/semester/{appId}/{choiceNo}")
    @ResponseStatus(HttpStatus.OK)
    public void changeSemester(@PathVariable Long appId, @PathVariable Integer choiceNo) {
        erasmusApplicationService.changeSemester(appId, choiceNo);
    }

    @GetMapping("/cancelChoice/semester/{appId}/{choiceNo}")
    @ResponseStatus(HttpStatus.OK)
    public void cancelChoice(@PathVariable Long appId, @PathVariable Integer choiceNo) {
        erasmusApplicationService.cancelChoice(appId, choiceNo);
    }
}
