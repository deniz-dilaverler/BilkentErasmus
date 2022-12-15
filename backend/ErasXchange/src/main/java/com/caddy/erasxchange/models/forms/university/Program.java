package com.caddy.erasxchange.models.forms.university;

import com.caddy.erasxchange.models.BaseEntity;
import com.caddy.erasxchange.models.Department;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "programs")
public class Program  extends BaseEntity {

    @Column(name = "quota")
    private Integer quota;



    @Column(name = "department")
    @Enumerated(EnumType.STRING)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uni_id")
    private University university;

}