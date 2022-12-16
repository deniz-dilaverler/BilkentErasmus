package com.caddy.erasxchange.mappers;

import com.caddy.erasxchange.DTOs.BilateralUniversityDto;
import com.caddy.erasxchange.models.course.ExternalCourse;
import com.caddy.erasxchange.models.university.BilateralUniversity;
import com.caddy.erasxchange.models.users.Coordinator;
import com.caddy.erasxchange.services.courses.ExternalCourseService;
import com.caddy.erasxchange.services.user.CoordinatorService;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = { CoordinatorService.class, ExternalCourseService.class})
public interface BilateralUniversityMapper {
    @Mapping(source="courseIds", target = "courses")
    @Mapping(source = "coordinatorIds", target = "coordinators")
    BilateralUniversity toEntity(BilateralUniversityDto bilateralUniversityDto);

    List<BilateralUniversity> toEntityList(List<BilateralUniversityDto> bilateralUniversityDto);

    @Mapping(source = "courses", target ="courseIds" )
    @Mapping(source = "coordinators", target = "coordinatorIds")
    BilateralUniversityDto toDto(BilateralUniversity bilateralUniversity);
    List<BilateralUniversityDto> toDtoList (List<BilateralUniversity> bilateralUniversity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "coordinatorIds", target = "coordinators")
    BilateralUniversity partialUpdate(BilateralUniversityDto bilateralUniversityDto, @MappingTarget BilateralUniversity bilateralUniversity);

    default Set<Long> coursesToCourseIds(Set<ExternalCourse> courses) {
        return courses.stream().map(ExternalCourse::getId).collect(Collectors.toSet());
    }


    default Set<Long> map(Set<Coordinator> coordinators) {
        return coordinators.stream().map(Coordinator::getId).collect(Collectors.toSet());
    }
}