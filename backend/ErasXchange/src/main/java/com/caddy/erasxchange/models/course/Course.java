package com.caddy.erasxchange.models.course;

import com.caddy.erasxchange.models.BaseEntity;
import com.caddy.erasxchange.models.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
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