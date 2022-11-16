package com.caddy.erasxchange.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "forms")
public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "date_sent")
    private Instant dateSent;

    @Column(name = "status")
    @Type(type = "org.hibernate.type.TextType")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User senderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiverId;

/*
    TODO [JPA Buddy] create field to map the 'external_courses' column
     Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "external_courses", columnDefinition = "int4[]")
    private Object externalCourses;
*/
/*
    TODO [JPA Buddy] create field to map the 'bilkent_courses' column
     Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "bilkent_courses", columnDefinition = "int4[]")
    private Object bilkentCourses;
*/
}