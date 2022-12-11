package com.caddy.erasxchange.models.course;

import com.caddy.erasxchange.models.BaseEntity;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Course extends BaseEntity {

    @Column(name = "name")
    @Type(type = "org.hibernate.type.TextType")
    private String name;

    @Column(name = "ects")
    private Double ects;

    @Column(name = "normal_credit")
    private Double normalCredit;
    
    
}