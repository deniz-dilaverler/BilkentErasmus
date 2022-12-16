package com.caddy.erasxchange.DTOs;

import com.caddy.erasxchange.models.Semester;
import com.caddy.erasxchange.models.university.BilateralUniversity;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link BilateralUniversity} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class BilateralUniversityDto implements Serializable {
    private Long id;
    private String name;
    private Set<Long> coordinatorIds;
    private String languageRequirement;
    private Semester semester;
    private String country;
    private Set<Long> courseIds;
    private int quota;
}