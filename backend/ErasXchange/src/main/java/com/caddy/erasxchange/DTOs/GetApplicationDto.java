package com.caddy.erasxchange.DTOs;

import com.caddy.erasxchange.models.Semester;
import com.caddy.erasxchange.models.application.AppStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

@Data
/**
 * A DTO for the {@link com.caddy.erasxchange.models.application.Application} entity
 */
public class GetApplicationDto implements Serializable {
    private final Long id;
    private final Long studentId;
    private final AppStatus status;
    private final Semester semester;
    private final Long choice1Id;
    private final Long choice2Id;
    private final Long choice3Id;
    private final Long choice4Id;
    private final Long choice5Id;
    private final Long placedSchoolId;

    private final Boolean isBilateral;




}