package com.caddy.erasxchange.models.course;

import com.caddy.erasxchange.models.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "equyivalence_item")
public class EquivalenceItem extends BaseEntity {
    @ManyToOne( cascade = javax.persistence.CascadeType.PERSIST)
    @JoinColumn(name = "external_course_id")
    @Cascade(CascadeType.PERSIST)
    ExternalCourse externalCourse;

    @ManyToOne( cascade = javax.persistence.CascadeType.PERSIST)
    @JoinColumn(name = "bilkent_course_id")
    BilkentCourse bilkentCourse;

    ApprovalStatus approvalStatus;

}