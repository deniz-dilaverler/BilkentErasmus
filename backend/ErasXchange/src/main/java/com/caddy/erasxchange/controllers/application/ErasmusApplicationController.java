package com.caddy.erasxchange.controllers.application;


import com.caddy.erasxchange.DTOs.ErasmusApplicationDto;
import com.caddy.erasxchange.models.Department;
import com.caddy.erasxchange.services.application.ErasmusApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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



}
