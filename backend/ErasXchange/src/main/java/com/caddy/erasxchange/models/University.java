package com.caddy.erasxchange.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "universities")
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    @Type(type = "org.hibernate.type.TextType")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coordinator_bilkent_id", referencedColumnName = "bilkent_id")
    private Coordinator coordinatorBilkentId;

    @Column(name = "is_bilateral")
    private Boolean isBilateral;
    @Column(name = "quota")
    private Integer quota;

/*
    TODO [JPA Buddy] create field to map the 'applicable_semesters' column
     Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "applicable_semesters", columnDefinition = "text[]")
    private Object applicableSemesters;
*/
}