package com.caddy.erasxchange.models.application;

import com.caddy.erasxchange.models.university.BilateralUniversity;
import com.caddy.erasxchange.models.users.Student;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "bilateral_application")
public class BilateralApplication extends Application {
    //needed for mapstruct to see this method


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "choice1")
    private BilateralUniversity choice1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "choice2")
    private BilateralUniversity choice2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "choice3")
    private BilateralUniversity choice3;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "choice4")
    private BilateralUniversity choice4;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "choice5")
    private BilateralUniversity choice5;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "placed_school")
    private BilateralUniversity placedSchool;


    // for mapstruct to be able to see these methods
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
        placedSchool =null;
    }
}