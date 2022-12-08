package com.caddy.erasxchange.models.users;

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