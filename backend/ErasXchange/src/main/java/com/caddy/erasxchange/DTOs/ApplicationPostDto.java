package com.caddy.erasxchange.DTOs;

import com.caddy.erasxchange.models.Semester;
import com.caddy.erasxchange.models.application.AppStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * A DTO for the {@link com.caddy.erasxchange.models.application.ErasmusApplication} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApplicationPostDto implements Serializable {
    private Long studentId;
    private AppStatus status;
    private Semester semester;
    private Long choice1Id;
    private Long choice2Id;
    private Long choice3Id;
    private Long choice4Id;
    private Long choice5Id;
    private Long placedSchoolId;
}