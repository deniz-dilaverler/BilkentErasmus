package com.caddy.erasxchange.mappers;

import com.caddy.erasxchange.DTOs.ApplicationDto;
import com.caddy.erasxchange.models.application.Application;
import com.caddy.erasxchange.models.application.ErasmusApplication;
import com.caddy.erasxchange.services.user.StudentService;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {StudentService.class})
public interface ErasmusApplicationMapper {
    @Mapping(source = "studentId", target = "student")
    ErasmusApplication toEntity(ApplicationDto applicationDto);

    @Mapping(source = "student.id", target = "studentId")
    ApplicationDto toDto(ErasmusApplication application);

    @Mapping(source = "studentId", target = "student")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ErasmusApplication updateApplicationFromApplicationDto(ApplicationDto applicationDto, @MappingTarget ErasmusApplication application);
}
