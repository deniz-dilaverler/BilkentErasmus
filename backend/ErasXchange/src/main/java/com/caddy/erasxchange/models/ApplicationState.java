package com.caddy.erasxchange.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/*
* this entity holds the state for certian applicaiton states to be used
* */
@Entity
@Getter
@NoArgsConstructor
@Setter
public class ApplicationState extends  BaseEntity {

    @ElementCollection
    @MapKeyColumn(name = "enum_value")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Map<Department, Boolean> erasmusAppsPlaced = new HashMap<Department, Boolean>();

    @ElementCollection
    @MapKeyEnumerated
    @MapKeyClass(Department.class)
    @MapKeyColumn(name = "key_column_3")
    @MapKeyJoinColumn(name = "key_column_4")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Map<Department, Boolean> bilateralAppsPlaced = new HashMap<Department, Boolean>();




}

class States {
    private Department department;
    private boolean isPlaced;
}
