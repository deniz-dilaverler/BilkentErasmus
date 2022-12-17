package com.caddy.erasxchange.models.university;

import com.caddy.erasxchange.models.BaseEntity;
import com.caddy.erasxchange.models.Department;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "programs")
public class Program  extends BaseEntity {

    @Column(name = "quota")
    private Integer quota;



    @Column(name = "department")
    @Enumerated(EnumType.STRING)
    private Department department;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uni_id")
    private ErasmusUniversity university;

}