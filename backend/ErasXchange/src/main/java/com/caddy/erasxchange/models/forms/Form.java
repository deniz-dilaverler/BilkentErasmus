package com.caddy.erasxchange.models.forms;

import com.caddy.erasxchange.models.BaseEntity;
import com.caddy.erasxchange.models.Users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Instant;


enum FormApprovalStatus  {PENDING, REJECTED, APPROVED}
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@MappedSuperclass
public abstract class Form extends BaseEntity {


    @Column(name = "time_sent")
    private Instant timeSent;

    @Column(name = "status")
    @Type(type = "org.hibernate.type.TextType")
    private FormApprovalStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;

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