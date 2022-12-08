package com.caddy.erasxchange.mappers;

import com.caddy.erasxchange.DTOs.ApplicationDto;
import com.caddy.erasxchange.models.application.Application;
import com.caddy.erasxchange.models.application.BilateralApplication;
import com.caddy.erasxchange.models.application.ErasmusApplication;
import com.caddy.erasxchange.services.user.StudentService;
import org.mapstruct.*;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {StudentService.class})
public interface BilateralApplicationMapper {


        @Mapping(source = "studentId", target = "student")
        BilateralApplication toEntity(ApplicationDto applicationDto);

        @Mapping(source = "student.id", target = "studentId")
        ApplicationDto toDto(BilateralApplication application);

        @Mapping(source = "studentId", target = "student")
        @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
        BilateralApplication updateApplicationFromApplicationDto(ApplicationDto applicationDto, @MappingTarget BilateralApplication application);

}
