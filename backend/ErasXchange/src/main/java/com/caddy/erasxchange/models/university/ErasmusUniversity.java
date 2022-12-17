package com.caddy.erasxchange.models.university;

import com.caddy.erasxchange.models.course.ExternalCourse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Accessors( chain = true)
@Table(name = "erasmus_university")
public class ErasmusUniversity extends University {
    @OneToMany(mappedBy = "university", cascade = CascadeType.PERSIST,  fetch = FetchType.EAGER)
    private Set<Program> programs;

    private Integer allowence;

    @Override
    public Set<ExternalCourse> getCourses() {
        return super.getCourses();
    }

    @Override
    public ErasmusUniversity setCourses(Set<ExternalCourse> courses) {
        super.setCourses(courses);
        return this;
    }
}