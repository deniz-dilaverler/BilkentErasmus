package com.caddy.erasxchange.DTOs;

import com.caddy.erasxchange.models.Department;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link com.caddy.erasxchange.models.course.BilkentCourse} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class BilkentCoursePostDto implements Serializable {
    private String name;
    private Department department;
    private String courseCode;
    private Double ects;
    private Double normalCredit;
    private String coordinatorName;
    private String coordinatorMail;
    private Set<EquivalenceItemDto> externalCourses;
}