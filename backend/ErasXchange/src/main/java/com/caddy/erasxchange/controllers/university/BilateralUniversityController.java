package com.caddy.erasxchange.controllers.university;


import com.caddy.erasxchange.DTOs.BilateralUniversityDto;
import com.caddy.erasxchange.services.university.BilateralUniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/university/bilateral")
public class BilateralUniversityController {

    final BilateralUniversityService bilateralUniversityService;

    @Autowired
    public BilateralUniversityController(BilateralUniversityService bilateralUniversityService) {
        this.bilateralUniversityService = bilateralUniversityService;
    }

    @GetMapping(value = "/{uniId}")
    public ResponseEntity<BilateralUniversityDto> getUniversity(@PathVariable Long uniId) {

        BilateralUniversityDto bilateralUniversityDto = bilateralUniversityService.getUniversity(uniId);

        return new ResponseEntity<BilateralUniversityDto>(bilateralUniversityDto, HttpStatus.OK);

    }

    @PostMapping("/")
    public ResponseEntity addUniversity(@RequestBody BilateralUniversityDto universityDto) {

        bilateralUniversityService.addUniversity(universityDto);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BilateralUniversityDto>> getUniversities() {


        List<BilateralUniversityDto> universities = bilateralUniversityService.getUniversities();

        return new ResponseEntity<List<BilateralUniversityDto>>(universities, HttpStatus.OK);
    }

}
