package com.caddy.erasxchange.DTOs;

import com.caddy.erasxchange.models.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * A DTO for the {@link com.caddy.erasxchange.models.Program} entity
 */
@AllArgsConstructor

@Getter
@Setter
public class ProgramDto implements Serializable {
    final private Long id;
    final private Integer quota;
    final private String name;
    final private Department department;
    final private Long universityId;
}