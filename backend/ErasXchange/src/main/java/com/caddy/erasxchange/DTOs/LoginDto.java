package com.caddy.erasxchange.DTOs;

import com.caddy.erasxchange.models.users.User;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

/**
 * A DTO for the {@link User} entity
 */
@Data
@Getter
public class LoginDto implements Serializable {
    private final String username;
    private final String password;
}