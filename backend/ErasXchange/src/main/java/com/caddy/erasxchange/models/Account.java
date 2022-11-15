package com.caddy.erasxchange.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "account")
public abstract class Account extends BaseEntity {
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String password;
    //private Set<Forms> forms;
    @Column(name= "bilkent_id")
    private int bilkentId;
    //department Enum mı olsun String mi yoksa ayrı bir class mı, department tableı olabilir belki
    // Enum olarak access yetkileri verilebilir, ya da enum set'i olabilir


}