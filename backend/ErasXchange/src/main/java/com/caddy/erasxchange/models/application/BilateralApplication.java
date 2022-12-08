package com.caddy.erasxchange.models.application;

import com.caddy.erasxchange.models.users.Student;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "bilateral_application")
public class BilateralApplication extends Application {
    //needed for mapstruct to see this method
    @Override
    public Student getStudent() {
        return super.getStudent();
    }

    @Override
    public void setStudent(Student student) {
        super.setStudent(student);
    }
}