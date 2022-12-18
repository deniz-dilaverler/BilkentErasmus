package com.caddy.erasxchange.mappers;

import com.caddy.erasxchange.DTOs.AddErasmusUniversityDto;
import com.caddy.erasxchange.DTOs.ErasmusUniversityDto;
import com.caddy.erasxchange.models.course.ExternalCourse;
import com.caddy.erasxchange.models.university.ErasmusUniversity;
import com.caddy.erasxchange.models.users.Coordinator;
import com.caddy.erasxchange.services.courses.ExternalCourseService;
import com.caddy.erasxchange.services.user.CoordinatorService;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring",
        uses = {ProgramMapper.class, CoordinatorService.class, ExternalCourseService.class})
public interface ErasmusUniversityMapper {

    ErasmusUniversity addToEntity(AddErasmusUniversityDto addErasmusUniversityDto);



    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ErasmusUniversity partialUpdate(AddErasmusUniversityDto addErasmusUniversityDto, @MappingTarget ErasmusUniversity erasmusUniversity);

    @Mapping(source = "courseIds", target = "courses")
    @Mapping(source = "coordinatorIds", target = "coordinators")
    ErasmusUniversity toEntity(ErasmusUniversityDto erasmusUniversityDto);

    List<ErasmusUniversity> toEntityList(List<ErasmusUniversityDto> erasmusUniversityDtos);

    // @Mapping(target = "courseIds", expression = "java(coursesToCourseIds(erasmusUniversity.getCourses()))")
    @Mapping(source = "courses", target = "courseIds")
    @Mapping(source = "coordinators", target = "coordinatorIds")
    @Mapping(constant = "true", target = "isErasmus")
    //@Mapping(expression = "java(coordinatorsToCoordinatorIds(erasmusUniversity.getCoordinators()))", target = "coordinatorIds")
    ErasmusUniversityDto toDto(ErasmusUniversity erasmusUniversity);

    List<ErasmusUniversityDto> toDtoList(List<ErasmusUniversity> erasmusUniversity);



    default Set<Long> coursesToCourseIds(Set<ExternalCourse> courses) {
        return courses.stream().map(ExternalCourse::getId).collect(Collectors.toSet());
    }

    default Set<Long> map(Set<Coordinator> coordinators) {
        return coordinators.stream().map(Coordinator::getId).collect(Collectors.toSet());
    }





            /*
    default Set<Long> coordinatorsToCoordinatorIds(Set<Coordinator> coordinators) {
        return coordinators.stream().map(Coordinator::getId).collect(Collectors.toSet());
    }

     */
}