package com.caddy.erasxchange.models;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity
{
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

}