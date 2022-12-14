package com.caddy.erasxchange.models.course;

import com.caddy.erasxchange.models.university.University;
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

    @ManyToMany
    @JoinTable(
            name = "external_bilkent_course",
            joinColumns = { @JoinColumn(name = "external_course_id")},
            inverseJoinColumns = {@JoinColumn(name = "bilkent_course_id")}
    )
    private Set<BilkentCourse> equivalentCourses;
}