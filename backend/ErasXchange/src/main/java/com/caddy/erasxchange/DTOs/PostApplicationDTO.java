package com.caddy.erasxchange.DTOs;

import com.caddy.erasxchange.models.Semester;
import com.caddy.erasxchange.models.application.AppStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data
public class PostApplicationDTO {
    private final Long studentId;
    private final AppStatus status;
    private final Semester semester;
    private final Long choice1Id;
    private final Long choice2Id;
    private final Long choice3Id;
    private final Long choice4Id;
    private final Long choice5Id;
}
