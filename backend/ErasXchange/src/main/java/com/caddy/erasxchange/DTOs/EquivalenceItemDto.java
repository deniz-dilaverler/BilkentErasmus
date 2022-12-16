package com.caddy.erasxchange.DTOs;

import com.caddy.erasxchange.models.course.ApprovalStatus;
import com.caddy.erasxchange.models.course.EquivalenceItem;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * A DTO for the {@link EquivalenceItem} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class EquivalenceItemDto implements Serializable {
    private Long id;
    private Long externalCourseId;
    private Long bilkentCourseId;
    private ApprovalStatus approvalStatus;
}