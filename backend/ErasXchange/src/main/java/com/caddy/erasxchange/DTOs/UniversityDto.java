package com.caddy.erasxchange.DTOs;

import com.caddy.erasxchange.DTOs.ProgramDto;
import com.caddy.erasxchange.models.Semester;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link com.caddy.erasxchange.models.University} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class UniversityDto implements Serializable {
    private Long id;
    private String name;
    private Long coordinatorId;
    private Boolean isBilateral;
    private Integer quota;
    private Semester semester;
    private Set<ProgramDto> programs;
    private Integer allowence;
    private String country;
    private Set<Long> coursIds;
}