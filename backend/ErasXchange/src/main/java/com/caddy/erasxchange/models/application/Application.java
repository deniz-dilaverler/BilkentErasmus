package com.caddy.erasxchange.models.application;

import com.caddy.erasxchange.models.BaseEntity;
import com.caddy.erasxchange.models.Department;
import com.caddy.erasxchange.models.Semester;
import com.caddy.erasxchange.models.users.Student;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;

import javax.persistence.*;




@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass

public abstract class Application extends BaseEntity implements  Comparable<Application>{

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    @JsonManagedReference
    private Student student;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AppStatus status;

    @Column(name = "semester1")
    @Enumerated(EnumType.STRING)
    private Semester semester1;

    @Column(name = "semester2")
    @Enumerated(EnumType.STRING)
    private Semester semester2;

    @Column(name = "semester3")
    @Enumerated(EnumType.STRING)
    private Semester semester3;

    @Column(name = "semester4")
    @Enumerated(EnumType.STRING)
    private Semester semester4;

    @Column(name = "semester5")
    @Enumerated(EnumType.STRING)
    private Semester semester5;


    @Override
    public int compareTo(Application app) {
        if (this.student.getExchangeScore() > app.getStudent().getExchangeScore()) return 1;
        else if (this.student.getExchangeScore().equals(app.getStudent().getExchangeScore())) return 0;
        else  return -1;
    }

    public Department getDepartment() {
        return this.student.getDepartment();
    }
    public abstract void setPlacedSchoolToNull();
}