package com.caddy.erasxchange.models.Users;

import com.caddy.erasxchange.models.BaseEntity;
import com.caddy.erasxchange.models.Department;
import com.caddy.erasxchange.models.forms.Form;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
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

    //Todo: bu form ile user arası ilişkiyi sağla
    //@ManyToOne
    //private List<Form> forms;

    // Todo: Event Classını Ekle ve bağlantıyı sağla

    //private List<Event> events;

    // TODO: add permission property to check what operations they can do

}