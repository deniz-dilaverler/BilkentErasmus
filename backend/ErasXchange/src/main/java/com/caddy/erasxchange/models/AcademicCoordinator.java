package com.caddy.erasxchange.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "academic_coordinators")
public class AcademicCoordinator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    @Type(type = "org.hibernate.type.TextType")
    private String name;

    @Column(name = "surname")
    @Type(type = "org.hibernate.type.TextType")
    private String surname;

    @Column(name = "email")
    @Type(type = "org.hibernate.type.TextType")
    private String email;

    @Column(name = "bilkent_id")
    private Integer bilkentId;

    public AcademicCoordinator() {
    }

    public AcademicCoordinator(Long id, String name, String surname, String email, Integer bilkentId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.bilkentId = bilkentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getBilkentId() {
        return bilkentId;
    }

    public void setBilkentId(Integer bilkentId) {
        this.bilkentId = bilkentId;
    }

}