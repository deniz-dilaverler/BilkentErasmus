package com.caddy.erasxchange.models.forms;

import com.caddy.erasxchange.models.users.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CourseTransferForm extends Form{
    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
