package com.caddy.erasxchange.DTOs;

import lombok.Data;

@Data
public class FormItemSendDto {
    String courseCode;
    Integer ects;
    String eqivalentCourseCode;
    Integer equiEcts;
    String username;
}
