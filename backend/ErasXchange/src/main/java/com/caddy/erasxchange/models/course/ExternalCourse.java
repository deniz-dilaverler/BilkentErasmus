package com.caddy.erasxchange.models.course;

import com.caddy.erasxchange.models.university.University;
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
@Accessors(chain = true)
@Getter
@Setter
@Entity
@Table(name = "external_courses")
public class ExternalCourse extends Course {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uni_id")
    private University university;

    @Column(name = "is_project")
    private Boolean isProject;

    @Column(name = "approval_status")
    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;

    @Column(name = "syllabus_link")
    @Type(type = "org.hibernate.type.TextType")
    private String syllabusLink;

    private Boolean isErasmus;

    @OneToMany(mappedBy = "externalCourse")
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<EquivalenceItem> equivalentCourses = new HashSet<>();
}