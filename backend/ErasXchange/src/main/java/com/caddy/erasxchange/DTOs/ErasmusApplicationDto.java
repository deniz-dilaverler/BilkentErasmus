package com.caddy.erasxchange.DTOs;

import com.caddy.erasxchange.models.Semester;
import com.caddy.erasxchange.models.application.AppStatus;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * A DTO for the {@link com.caddy.erasxchange.models.application.ErasmusApplication} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ErasmusApplicationDto implements Serializable {
    private Long id;
    private Long studentId;
    private AppStatus status;
    private Semester semester1;
    private Semester semester2;
    private Semester semester3;
    private Semester semester4;
    private Semester semester5;
    private ErasmusUniversityDto choice1;
    private ErasmusUniversityDto choice2;
    private ErasmusUniversityDto choice3;
    private ErasmusUniversityDto choice4;
    private ErasmusUniversityDto choice5;
    private ErasmusUniversityDto placedSchool;
}