package com.caddy.erasxchange.models.users;

import com.caddy.erasxchange.models.university.University;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "coordinators")
public class Coordinator extends User {

    @ManyToMany(mappedBy = "coordinators", fetch = FetchType.EAGER)

    private Set<University> responsibleSchools  =  new HashSet<>() ;



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