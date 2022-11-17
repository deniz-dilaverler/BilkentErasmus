package com.caddy.erasxchange.models.forms;

import com.caddy.erasxchange.models.BaseEntity;
import com.caddy.erasxchange.models.accounts.Account;

import javax.persistence.*;

@Entity
@Table(name = "form")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Form extends BaseEntity {

    private Account sender;

    private Account reciever;
    

}
