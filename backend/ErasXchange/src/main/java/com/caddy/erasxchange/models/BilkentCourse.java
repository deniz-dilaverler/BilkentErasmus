package com.caddy.erasxchange.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bilkent_courses")
public class BilkentCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    @Type(type = "org.hibernate.type.TextType")
    private String name;

    @Column(name = "ects")
    private Double ects;

    @Column(name = "coordinator_name")
    @Type(type = "org.hibernate.type.TextType")
    private String coordinatorName;

    @Column(name = "coordinator_mail")
    @Type(type = "org.hibernate.type.TextType")
    private String coordinatorMail;

}