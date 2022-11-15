package com.caddy.erasxchange.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


enum EducationStatus {GRADUATE, UNDERGRADUATE}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student_account")
public class StudentAccount extends Account {
    @Column(name = "erasmus_points")
    private double erasmusPoints;
    // private Set<MobilityApplication> applications;
    @Enumerated(EnumType.ORDINAL)
    private EducationStatus educationStatus;
    @Column(name = "gpa")
    private double GPA;
}