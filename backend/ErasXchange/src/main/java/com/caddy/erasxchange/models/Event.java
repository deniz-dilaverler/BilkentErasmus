package com.caddy.erasxchange.models;

import com.caddy.erasxchange.models.Users.User;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "event")
public class Event extends BaseEntity {

    private Instant eventTime;

    private String title;

    private String contents;

    @ManyToMany(mappedBy = "events" )
    private Set<User> people;

}