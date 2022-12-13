package com.caddy.erasxchange.mappers;

import com.caddy.erasxchange.DTOs.LoginDto;
import com.caddy.erasxchange.models.users.*;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
    Student toStudent(LoginDto loginDto);
    Coordinator toCoordinator(LoginDto loginDto);
    BoardMember toBoardMember(LoginDto loginDto);
    Iso toIso(LoginDto loginDto);

    LoginDto toDto(User user);
}