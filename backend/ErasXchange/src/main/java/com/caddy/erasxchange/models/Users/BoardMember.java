package com.caddy.erasxchange.models.Users;

import com.caddy.erasxchange.models.BaseEntity;
import com.caddy.erasxchange.models.Users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "board_members")
public class BoardMember extends User {



}