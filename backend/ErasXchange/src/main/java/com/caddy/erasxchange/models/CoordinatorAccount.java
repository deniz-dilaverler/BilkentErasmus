package com.caddy.erasxchange.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "coordinator_account")
public class CoordinatorAccount extends Account {
    // Set<University> responsible schools
}