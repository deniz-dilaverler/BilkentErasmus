package com.caddy.erasxchange.models;

import com.caddy.erasxchange.models.users.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event")
public class Event extends BaseEntity {
    public Event(Instant eventTime, String title, String contents) {
        super();
        this.eventTime = eventTime;
        this.title = title;
        this.contents = contents;
    }

    private Instant eventTime;

    private String title;

    private String contents;

    @ManyToMany(mappedBy = "events")
    private Set<User> people;

}