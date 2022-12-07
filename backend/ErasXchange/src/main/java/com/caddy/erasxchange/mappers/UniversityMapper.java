package com.caddy.erasxchange.mappers;

import com.caddy.erasxchange.DTOs.UniversityDto;
import com.caddy.erasxchange.models.University;
import com.caddy.erasxchange.models.course.ExternalCourse;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UniversityMapper {
    University universityDtoToUniversity(UniversityDto universityDto);

    @Mapping(target = "coursIds", expression = "java(coursesToCoursIds(university.getCourses()))")
    UniversityDto universityToUniversityDto(University university);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    University updateUniversityFromUniversityDto(UniversityDto universityDto, @MappingTarget University university);

    @AfterMapping
    default void linkPrograms(@MappingTarget University university) {
        university.getPrograms().forEach(program -> program.setUniversity(university));
    }

    default Set<Long> coursesToCoursIds(Set<ExternalCourse> courses) {
        return courses.stream().map(ExternalCourse::getId).collect(Collectors.toSet());
    }
}
