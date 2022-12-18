package com.caddy.erasxchange.models.course;

import com.caddy.erasxchange.models.BaseEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "equivalence_item")
public class EquivalenceItem extends BaseEntity {
    @ManyToOne( cascade = javax.persistence.CascadeType.PERSIST)
    @JoinColumn(name = "external_course_id")
    @Cascade(CascadeType.PERSIST)
    ExternalCourse externalCourse;

    @ManyToOne( cascade = javax.persistence.CascadeType.PERSIST)
    @JoinColumn(name = "bilkent_course_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    BilkentCourse bilkentCourse;
    @Enumerated(EnumType.STRING)
    ApprovalStatus approvalStatus;

}