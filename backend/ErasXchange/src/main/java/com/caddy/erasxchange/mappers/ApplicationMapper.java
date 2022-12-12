package com.caddy.erasxchange.mappers;

import com.caddy.erasxchange.DTOs.ApplicationDto;
import com.caddy.erasxchange.models.application.Application;
import com.caddy.erasxchange.models.application.BilateralApplication;
import com.caddy.erasxchange.models.application.ErasmusApplication;
import com.caddy.erasxchange.services.user.StudentService;
import org.mapstruct.*;

import java.util.List;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {StudentService.class, UniversityMapper.class})
public interface ApplicationMapper {


        @Mapping(source = "studentId", target = "student")
        BilateralApplication toBilateralApp(ApplicationDto applicationDto);
        @Mapping(source = "studentId", target = "student")
        ErasmusApplication toErasmusApp(ApplicationDto applicationDto);

        @Mapping(source = "student.id", target = "studentId")
        ApplicationDto toDto(Application application);


        List<ApplicationDto> toDtoList(List<Application> applications);
        @Mapping(source = "studentId", target = "student")
        @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
        BilateralApplication updateBilateralApplication(ApplicationDto applicationDto, @MappingTarget BilateralApplication application);
        @Mapping(source = "studentId", target = "student")
        @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
        ErasmusApplication updateErasmusApplication(ApplicationDto applicationDto, @MappingTarget ErasmusApplication application);

}
