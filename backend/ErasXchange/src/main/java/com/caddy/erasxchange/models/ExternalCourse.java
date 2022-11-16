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
@Table(name = "external_courses")
public class ExternalCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    @Type(type = "org.hibernate.type.TextType")
    private String name;

    @Column(name = "ects")
    private Double ects;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uni_id")
    private University uniId;

    @Column(name = "is_project")
    private Boolean isProject;

    @Column(name = "approval_status")
    @Type(type = "org.hibernate.type.TextType")
    private String approvalStatus;

    @Column(name = "syllabus_link")
    @Type(type = "org.hibernate.type.TextType")
    private String syllabusLink;

}