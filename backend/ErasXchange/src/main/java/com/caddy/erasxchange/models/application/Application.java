package com.caddy.erasxchange.models.application;

import com.caddy.erasxchange.models.BaseEntity;
import com.caddy.erasxchange.models.Semester;
import com.caddy.erasxchange.models.users.Student;
import com.caddy.erasxchange.models.university.University;
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
    private Student student;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AppStatus status;

    @Column(name = "semester")
    @Enumerated(EnumType.STRING)
    private Semester semester;


    @Override
    public int compareTo(Application app) {
        if (this.student.getExchangeScore() > app.getStudent().getExchangeScore()) return 1;
        else if (this.student.getExchangeScore() == app.getStudent().getExchangeScore()) return 0;
        else  return -1;
    }

    public abstract void setPlacedSchoolToNull();
}