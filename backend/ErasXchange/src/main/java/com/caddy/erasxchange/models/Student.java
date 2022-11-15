package com.caddy.erasxchange.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "students")
public class Student {
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

    @Column(name = "bilkent_id", nullable = false)
    private int bilkentId;

    public Student() {
    }

    public Student(Long id, String name, String surname, String email, int bilkentId) {
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

    public int getBilkentId() {
        return bilkentId;
    }

    public void setBilkentId(int bilkentId) {
        this.bilkentId = bilkentId;
    }


}