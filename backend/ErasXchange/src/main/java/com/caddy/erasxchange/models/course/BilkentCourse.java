package com.caddy.erasxchange.models.course;

import com.caddy.erasxchange.models.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bilkent_courses")
public class BilkentCourse extends Course{

    @Column(name = "coordinator_name")
    @Type(type = "org.hibernate.type.TextType")
    private String coordinatorName;

    @Column(name = "coordinator_mail")
    @Type(type = "org.hibernate.type.TextType")
    private String coordinatorMail;


    @ManyToMany(mappedBy = "equivalentCourses")
    private Set<ExternalCourse> externalCourses;




}