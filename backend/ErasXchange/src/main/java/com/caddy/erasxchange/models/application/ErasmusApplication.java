package com.caddy.erasxchange.models.application;

import com.caddy.erasxchange.models.users.Student;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "erasmus_application")
public class ErasmusApplication extends Application {

    @Override
    public Student getStudent() {
        return super.getStudent();
    }

    @Override
    public void setStudent(Student student) {
        super.setStudent(student);
    }
}