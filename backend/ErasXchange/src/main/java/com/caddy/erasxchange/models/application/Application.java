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

public abstract class Application extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AppStatus status;

    @Column(name = "semester")
    @Enumerated(EnumType.STRING)
    private Semester semester;


}