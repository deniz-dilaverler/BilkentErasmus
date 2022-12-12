package com.caddy.erasxchange.mappers;

import com.caddy.erasxchange.DTOs.BilateralUniversityDto;
import com.caddy.erasxchange.models.course.ExternalCourse;
import com.caddy.erasxchange.models.university.BilateralUniversity;
import com.caddy.erasxchange.services.courses.ExternalCourseService;
import com.caddy.erasxchange.services.user.CoordinatorService;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = { CoordinatorService.class, ExternalCourseService.class})
public interface BilateralUniversityMapper {
    @Mapping(source="courseIds", target = "courses")
    @Mapping(source = "coordinatorId", target = "coordinator.id")
    BilateralUniversity toEntity(BilateralUniversityDto bilateralUniversityDto);

    List<BilateralUniversity> toEntityList(BilateralUniversityDto bilateralUniversityDto);

    @Mapping(source = "courses", target ="courseIds" )
    @Mapping(source = "coordinator.id", target = "coordinatorId")
    BilateralUniversityDto toDto(BilateralUniversity bilateralUniversity);
    List<BilateralUniversityDto> toDtoList (List<BilateralUniversity> bilateralUniversity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "coordinatorId", target = "coordinator.id")
    BilateralUniversity partialUpdate(BilateralUniversityDto bilateralUniversityDto, @MappingTarget BilateralUniversity bilateralUniversity);

    default Set<Long> coursesToCoursIds(Set<ExternalCourse> courses) {
        return courses.stream().map(ExternalCourse::getId).collect(Collectors.toSet());
    }
}