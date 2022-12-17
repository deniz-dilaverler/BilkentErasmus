package com.caddy.erasxchange.models.university;

import com.caddy.erasxchange.models.BaseEntity;
import com.caddy.erasxchange.models.Semester;
import com.caddy.erasxchange.models.course.ExternalCourse;
import com.caddy.erasxchange.models.users.Coordinator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cascade;
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
@Table(name = "universities")
@Accessors( chain = true)
@Inheritance(strategy = InheritanceType.JOINED)
public  abstract class University extends BaseEntity {

    @Column(name = "name")
    @Type(type = "org.hibernate.type.TextType")
    private String name;

    @ManyToMany()
    @JoinTable(
            name = "universities_join_coordinators",
            joinColumns = { @JoinColumn(name = "university_id")},
            inverseJoinColumns = {@JoinColumn(name = "coordinator_id")}
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Coordinator> coordinators = new HashSet<>();



    private String languageRequirement;
    private Semester semester;


    private String country;

    @OneToMany( cascade = CascadeType.PERSIST)
    private Set<ExternalCourse> courses;




}