package com.caddy.erasxchange.controllers.university;


import com.caddy.erasxchange.DTOs.AddErasmusUniversityDto;
import com.caddy.erasxchange.DTOs.ErasmusUniversityDto;
import com.caddy.erasxchange.DTOs.ProgramDto;
import com.caddy.erasxchange.models.university.ErasmusUniversity;
import com.caddy.erasxchange.services.university.ErasmusUniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/university/erasmus")
public class ErasmusUniversityController {
    final ErasmusUniversityService erasmusUniversityService;

    @Autowired
    public ErasmusUniversityController(ErasmusUniversityService erasmusUniversityService) {
        this.erasmusUniversityService = erasmusUniversityService;
    }

    @GetMapping(value = "/{uniId}")
    public ResponseEntity<ErasmusUniversityDto> getUniversity(@PathVariable Long uniId) {

        ErasmusUniversityDto erasmusUni = erasmusUniversityService.getUniversity(uniId);

        return new ResponseEntity<ErasmusUniversityDto>(erasmusUni, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity addUniversity(@RequestBody AddErasmusUniversityDto university) {
        System.out.println(university);
        erasmusUniversityService.addUniversity(university);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ErasmusUniversityDto>> getUniversities() {


        List<ErasmusUniversityDto> universities = erasmusUniversityService.getUniversities();
        System.out.println(universities);

        for(ErasmusUniversityDto dto: universities) {
            System.out.println(dto.getPrograms().size());
        }
        System.out.println("Abu");
        return new ResponseEntity<List<ErasmusUniversityDto>>(universities, HttpStatus.OK);
    }

    @PutMapping("/program")
    public ResponseEntity addProgram(@RequestBody ProgramDto programDto) {
        erasmusUniversityService.addProgram(programDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
