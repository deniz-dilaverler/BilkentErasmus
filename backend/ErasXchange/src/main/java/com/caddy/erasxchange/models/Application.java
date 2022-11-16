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
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_bilkent_id", referencedColumnName = "bilkent_id")
    private Student studentBilkentId;

    @Column(name = "status")
    @Type(type = "org.hibernate.type.TextType")
    private String status;

    @Column(name = "semester")
    @Type(type = "org.hibernate.type.TextType")
    private String semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "choice1")
    private University choice1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "choice2")
    private University choice2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "choice3")
    private University choice3;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "choice4")
    private University choice4;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "choice5")
    private University choice5;

}