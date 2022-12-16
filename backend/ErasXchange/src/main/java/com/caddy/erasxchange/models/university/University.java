package com.caddy.erasxchange.models.university;

import com.caddy.erasxchange.models.BaseEntity;
import com.caddy.erasxchange.models.Semester;
import com.caddy.erasxchange.models.course.ExternalCourse;
import com.caddy.erasxchange.models.users.Coordinator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "universities")
@Inheritance(strategy = InheritanceType.JOINED)
public  abstract class University extends BaseEntity {

    @Column(name = "name")
    @Type(type = "org.hibernate.type.TextType")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "universities_join_coordinators",
            joinColumns = { @JoinColumn(name = "university_id")},
            inverseJoinColumns = {@JoinColumn(name = "coordinator_id")}
    )
    private Set<Coordinator> coordinators;




    private String languageRequirement;
    private Semester semester;


    private String country;

    @OneToMany(mappedBy = "university", cascade = CascadeType.PERSIST)
    private Set<ExternalCourse> courses;




}