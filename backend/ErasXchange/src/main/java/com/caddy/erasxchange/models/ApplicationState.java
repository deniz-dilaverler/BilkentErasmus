package com.caddy.erasxchange.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/*
* this entity holds the state for certian applicaiton states to be used
* */
@Entity
public class ApplicationState {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;



}
