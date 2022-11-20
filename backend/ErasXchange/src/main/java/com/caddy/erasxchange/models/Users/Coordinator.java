package com.caddy.erasxchange.models.Users;

import com.caddy.erasxchange.models.BaseEntity;
import com.caddy.erasxchange.models.University;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "coordinators")
public class Coordinator extends User {

    @OneToMany(mappedBy = "coordinator")
    private Set<University> responsibleSchools;
}