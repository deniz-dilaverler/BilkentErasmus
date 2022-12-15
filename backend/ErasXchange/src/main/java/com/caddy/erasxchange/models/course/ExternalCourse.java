package com.caddy.erasxchange.models.course;

import com.caddy.erasxchange.models.forms.university.University;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
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
    private Set<EquivalenceItem> equivalentCourses = new HashSet<>();
}