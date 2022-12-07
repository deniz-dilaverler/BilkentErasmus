package com.caddy.erasxchange.DTOs;

import com.caddy.erasxchange.models.Semester;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link com.caddy.erasxchange.models.University} entity
 */
@Getter
@Setter
@AllArgsConstructor
public class UniversityDto implements Serializable {
    final private Long id;
    final private String name;
    final private Boolean isBilateral;
    final private Integer quota;
    final private Semester semester;
    final private Set<ProgramDto> programs;
    final private Integer allowence;
    final private String country;
    final private Set<Long> coursIds;
}