package com.caddy.erasxchange.DTOs;

import com.caddy.erasxchange.models.Department;
import com.caddy.erasxchange.models.course.ApprovalStatus;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link com.caddy.erasxchange.models.course.ExternalCourse} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ExternalCourseDto implements Serializable {
    private Long id;
    private String name;
    private Department department;
    private String courseCode;
    private Double ects;
    private Double normalCredit;
    private Long universityId;
    private Boolean isProject;
    private ApprovalStatus approvalStatus;
    private String syllabusLink;
    private Boolean isErasmus;
    private Set<EquivalenceItemDto> equivalentCourses;
}