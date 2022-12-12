package com.caddy.erasxchange.models.university;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bilateral_university")
public class BilateralUniversity extends University {
    private int quota;
}