package com.caddy.erasxchange.models.Users;

import com.caddy.erasxchange.models.BaseEntity;
import com.caddy.erasxchange.models.Users.User;
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
public class Student extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bilkent_id", referencedColumnName = "bilkent_id")
    private User bilkentId;

    @Column(name = "exchange_score")
    private Double exchangeScore;

    @Column(name = "gpa")
    private Double gpa;


    @OneToOne
    @JoinColumn(name = "erasmus_application_id")
    private ErasmusApplication erasmusApplication;

    @OneToOne
    @JoinColumn(name = "bilateral_application_id")
    private BilateralApplication bilateralApplication;







}