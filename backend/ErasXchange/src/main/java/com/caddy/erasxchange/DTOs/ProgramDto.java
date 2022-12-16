package com.caddy.erasxchange.DTOs;

import com.caddy.erasxchange.models.Department;
import com.caddy.erasxchange.models.university.Program;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * A DTO for the {@link Program} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ProgramDto implements Serializable {
    private Long id;
    private Integer quota;
    private Department department;
    private Long universityId;
}