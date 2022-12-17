package com.caddy.erasxchange.DTOs;

import com.caddy.erasxchange.models.Semester;
import com.caddy.erasxchange.models.university.Program;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link com.caddy.erasxchange.models.university.ErasmusUniversity} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class AddErasmusUniversityDto implements Serializable {
    private String name;
    private String languageRequirement;
    private Semester semester;
    private String country;
    private Integer allowance;
    private Integer quota;
    private Long coordinatorId;
}