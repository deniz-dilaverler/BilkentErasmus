package com.caddy.erasxchange.models.forms;

import com.caddy.erasxchange.models.Users.Student;

import javax.persistence.OneToOne;

public class CourseTransferForm extends Form{
    @OneToOne
    Student student;
}
