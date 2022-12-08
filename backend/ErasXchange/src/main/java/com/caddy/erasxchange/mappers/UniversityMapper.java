package com.caddy.erasxchange.mappers;

import com.caddy.erasxchange.DTOs.UniversityDto;
import com.caddy.erasxchange.models.University;
import com.caddy.erasxchange.models.course.ExternalCourse;
import com.caddy.erasxchange.models.users.Coordinator;
import com.caddy.erasxchange.services.user.CoordinatorService;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {CoordinatorService.class})
public interface UniversityMapper {
    @Mapping(source = "coordinatorId", target = "coordinator")
    University universityDtoToUniversity(UniversityDto universityDto);

    @Mapping(source = "coordinator.id", target = "coordinatorId")
    @Mapping(target = "coursIds", expression = "java(coursesToCoursIds(university.getCourses()))")
    UniversityDto universityToUniversityDto(University university);

    @Mapping(source = "coordinatorId", target = "coordinator.id")
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
