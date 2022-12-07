package com.caddy.erasxchange.mappers;

import com.caddy.erasxchange.DTOs.ProgramDto;
import com.caddy.erasxchange.models.Program;
import com.caddy.erasxchange.models.course.ExternalCourse;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ProgramMapper {
    @Mapping(source = "universityId", target = "university.id")
    Program programDtoToProgram(ProgramDto programDto);

    @Mapping(source = "university.id", target = "universityId")
    ProgramDto programToProgramDto(Program program);

    @Mapping(source = "universityId", target = "university.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Program updateProgramFromProgramDto(ProgramDto programDto, @MappingTarget Program program);

    default Set<Long> coursesToCoursIds(Set<ExternalCourse> courses) {
        return courses.stream().map(ExternalCourse::getId).collect(Collectors.toSet());
    }
}
