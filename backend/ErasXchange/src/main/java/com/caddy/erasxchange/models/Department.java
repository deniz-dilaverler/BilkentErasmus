package com.caddy.erasxchange.models;


/*
TODO: ADdd the remainining Departments
 */
public enum Department {

    CS("CS"),
    IAED("IAED"),
    EEE("EEE"),
    ME("ME"),
    IE("IE"),
    MAN("MAN"),
    MATH("MATH");

    private String label;

    Department(String label) {
        this.label = label;
    }

}
