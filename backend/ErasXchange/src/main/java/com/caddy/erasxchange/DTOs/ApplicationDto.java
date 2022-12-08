package com.caddy.erasxchange.DTOs;

import com.caddy.erasxchange.models.Semester;
import com.caddy.erasxchange.models.application.AppStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * A DTO for the {@link com.caddy.erasxchange.models.application.Application} entity
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ApplicationDto implements Serializable {
    private Long id;
    private Long studentId;
    private AppStatus status;
    private Semester semester;
    private UniversityDto choice1;
    private UniversityDto choice2;
    private UniversityDto choice3;
    private UniversityDto choice4;
    private UniversityDto choice5;
    private UniversityDto placedSchool;
}