package com.caddy.erasxchange.DTOs;

import com.caddy.erasxchange.models.Semester;
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
public class ErasmusUniversityDto implements Serializable {
    private Long id;
    private String name;
    private Long coordinatorId;
    private String languageRequirement;
    private Semester semester;
    private String country;
    private Set<Long> courseIds;
    private Set<ProgramDto> programs;
    private Integer allowence;
}