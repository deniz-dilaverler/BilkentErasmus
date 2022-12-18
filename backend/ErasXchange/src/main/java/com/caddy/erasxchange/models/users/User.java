package com.caddy.erasxchange.models.users;

import com.caddy.erasxchange.models.BaseEntity;
import com.caddy.erasxchange.models.Department;
import com.caddy.erasxchange.models.Event;
import com.caddy.erasxchange.models.forms.Form;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "first_name")
    @Type(type = "org.hibernate.type.TextType")
    private String firstName;

    @Column(name = "last_name")
    @Type(type = "org.hibernate.type.TextType")
    private String lastName;

    @Column(name = "email")
    @Type(type = "org.hibernate.type.TextType")
    private String email;

    @Column(name = "password")
    @Type(type = "org.hibernate.type.TextType")
    private String password;

    @Column(name = "bilkent_id")
    private Integer bilkentId;

    @Column(name = "department")
    @Enumerated(EnumType.STRING)
    private Department department;

    @ManyToMany()
    @JoinTable(
            name = "users_events",
            joinColumns = { @JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "event_id")}
    )
    private List<Event> events; //list as the order of events are relevant


    //forms that the user is sending
    @OneToMany(mappedBy = "sender")
    private List<Form> senderForms;

    //forms that the user is reciving
    @OneToMany(mappedBy = "receiver")
    private List<Form> receiverForms;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;




    // TODO: add permission property to check what operations they can do

}