package com.caddy.erasxchange.models.users;

import com.caddy.erasxchange.models.application.BilateralApplication;
import com.caddy.erasxchange.models.application.ErasmusApplication;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "students")
public class Student extends User {

    @Column(name = "exchange_score")
    private Double exchangeScore;

    @Column(name = "gpa")
    private Double gpa;

    @OneToOne(mappedBy = "student")
    private ErasmusApplication erasmusApplication;

    @OneToOne(mappedBy = "student")
    private BilateralApplication bilateralApplication;



    // Id accessors must be in the child class for mapstruct to be able to access them
    @Override
    public void setId(Long id) {
        super.setId(id);
    }
    @Override
    public Long getId() {
        return super.getId();
    }
}