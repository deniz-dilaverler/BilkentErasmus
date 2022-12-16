package com.caddy.erasxchange.controllers.course;


import com.caddy.erasxchange.DTOs.EquivalenceItemDto;
import com.caddy.erasxchange.models.course.ApprovalStatus;
import com.caddy.erasxchange.models.course.EquivalenceItem;
import com.caddy.erasxchange.services.courses.EquivalenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equivalence")
public class EquivalenceController {

    final EquivalenceService equivalenceService;

    @Autowired
    public EquivalenceController(EquivalenceService equivalenceService) {
        this.equivalenceService = equivalenceService;
    }

    @PutMapping("/approve/{equivalenceId}")
    public HttpStatus approveEquivalence(Long equivalenceId) {
        equivalenceService.approveEquivalence(equivalenceId);
        return HttpStatus.OK;
    }

    @PutMapping("/deny/{equivalenceId}")
    public HttpStatus denyEquivalence(Long equivalenceId ) {
       equivalenceService.denyEquivalence(equivalenceId);
       return HttpStatus.OK;
    }

    @GetMapping("/{equivalenceId}")
    public ResponseEntity<EquivalenceItemDto> getEquivalence(Long equivalenceId) {
        EquivalenceItemDto equivalenceItemDto =  equivalenceService.getEquivalence(equivalenceId);

        return new ResponseEntity<EquivalenceItemDto>(equivalenceItemDto, HttpStatus.OK);
    }

    @GetMapping("/bystatus/{approvalStatus}")
    public ResponseEntity<List<EquivalenceItemDto>> getEquivalencesByStatus(@PathVariable ApprovalStatus approvalStatus) {
        List<EquivalenceItemDto> equivalenceItemDtos = equivalenceService.getEquivalencesByStatus(approvalStatus);
        return new ResponseEntity<List<EquivalenceItemDto>>(equivalenceItemDtos, HttpStatus.OK);
    }

}
