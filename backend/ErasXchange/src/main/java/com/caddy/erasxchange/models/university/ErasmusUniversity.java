package com.caddy.erasxchange.models.university;

import com.caddy.erasxchange.models.course.ExternalCourse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "erasmus_university")
public class ErasmusUniversity extends University {
    @OneToMany(mappedBy = "university", cascade = CascadeType.PERSIST)

    private Set<Program> programs;

    private Integer allowence;

    @Override
    public Set<ExternalCourse> getCourses() {
        return super.getCourses();
    }

    @Override
    public void setCourses(Set<ExternalCourse> courses) {
        super.setCourses(courses);
    }
}