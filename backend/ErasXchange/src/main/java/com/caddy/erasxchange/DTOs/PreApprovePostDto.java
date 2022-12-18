package com.caddy.erasxchange.DTOs;

import com.caddy.erasxchange.models.course.ExternalCourse;
import lombok.Data;

import java.util.List;

@Data
public class PreApprovePostDto {
    private Integer senderBilkentId;
    private List<String> extCourseCodes;

    private List<String> bilkentCourseCodes;

}
