package com.caddy.erasxchange.models;

import com.caddy.erasxchange.models.users.Coordinator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "universities")
public class University extends BaseEntity{

    @Column(name = "name")
    @Type(type = "org.hibernate.type.TextType")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coordinator_id", referencedColumnName = "id")
    private Coordinator coordinator;

    @Column(name = "is_bilateral")
    private Boolean isBilateral;
    @Column(name = "quota")
    private Integer quota;

    private Semester semester;

    @OneToMany(mappedBy = "uni_id")
    private Set<Program> programs;

    private Integer allowence;
    private String country;




}