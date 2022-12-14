package com.caddy.erasxchange.models.application;

import com.caddy.erasxchange.models.BaseEntity;
import com.caddy.erasxchange.models.Semester;
import com.caddy.erasxchange.models.users.Student;
import com.caddy.erasxchange.models.University;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    private Student student;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AppStatus status;

    @Column(name = "semester")
    @Enumerated(EnumType.STRING)
    private Semester semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "choice1")
    private University choice1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "choice2")
    private University choice2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "choice3")
    private University choice3;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "choice4")
    private University choice4;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "choice5")
    private University choice5;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "placed_school")
    private University placedSchool;

}