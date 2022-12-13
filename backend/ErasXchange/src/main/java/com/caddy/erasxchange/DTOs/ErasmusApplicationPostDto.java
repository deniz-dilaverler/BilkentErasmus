package com.caddy.erasxchange.DTOs;

import com.caddy.erasxchange.models.Semester;
import com.caddy.erasxchange.models.application.AppStatus;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * A DTO for the {@link com.caddy.erasxchange.models.application.ErasmusApplication} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ErasmusApplicationPostDto implements Serializable {
    private Long studentId;
    private AppStatus status;
    private Semester semester;
    private Long choice1Id;
    private Long choice2Id;
    private Long choice3Id;
    private Long choice4Id;
    private Long choice5Id;
}