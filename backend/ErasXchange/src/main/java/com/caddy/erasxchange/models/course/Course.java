package com.caddy.erasxchange.models.course;

import com.caddy.erasxchange.models.BaseEntity;
import com.caddy.erasxchange.models.Department;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class Course extends BaseEntity {

    @Column(name = "name")
    @Type(type = "org.hibernate.type.TextType")
    private String name;

    @Enumerated(EnumType.STRING)
    private Department department;

    private String courseCode;

    @Column(name = "ects")
    private Double ects;

    @Column(name = "normal_credit")
    private Double normalCredit;
    
    
}