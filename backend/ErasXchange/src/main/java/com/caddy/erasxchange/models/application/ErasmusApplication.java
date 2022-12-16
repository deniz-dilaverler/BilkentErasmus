package com.caddy.erasxchange.models.application;

import com.caddy.erasxchange.models.university.ErasmusUniversity;
import com.caddy.erasxchange.models.users.Student;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "erasmus_application")
public class ErasmusApplication extends Application {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "choice1")
    private ErasmusUniversity choice1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "choice2")
    private ErasmusUniversity choice2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "choice3")
    private ErasmusUniversity choice3;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "choice4")
    private ErasmusUniversity choice4;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "choice5")
    private ErasmusUniversity choice5;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "placed_school")
    private ErasmusUniversity placedSchool;

    //for mapstruct to see these methods (mapstruct can't see parent methds)
    @Override
    public Student getStudent() {
        return super.getStudent();
    }

    @Override
    public void setStudent(Student student) {
        super.setStudent(student);
    }

    @Override
    public void setPlacedSchoolToNull() {
        setPlacedSchool(null);
    }
}