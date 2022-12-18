package com.caddy.erasxchange.models.forms;

import com.caddy.erasxchange.models.BaseEntity;
import com.caddy.erasxchange.models.course.BilkentCourse;
import com.caddy.erasxchange.models.course.ExternalCourse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *FormItem class abstracts every single row of the form classes
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "form_item")
public class FormItem extends BaseEntity {
    //Unidricetional mappings are implemented
    // credits of the courses can be fetched from the objects, no need for extra properties
    @OneToOne(targetEntity = ExternalCourse.class)
    private ExternalCourse externalCourse;

    @OneToOne(targetEntity = BilkentCourse.class)
    private BilkentCourse equivalentCourse;
}