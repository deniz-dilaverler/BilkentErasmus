package com.caddy.erasxchange.models.course;

import com.caddy.erasxchange.models.Department;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
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


    @OneToMany(mappedBy = "bilkentCourse")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<EquivalenceItem> externalCourses = new HashSet<>();

}