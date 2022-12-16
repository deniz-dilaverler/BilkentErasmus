package com.caddy.erasxchange.mappers;

import com.caddy.erasxchange.models.course.BilkentCourse;
import com.caddy.erasxchange.models.course.Course;
import com.caddy.erasxchange.models.course.EquivalenceItem;
import com.caddy.erasxchange.DTOs.EquivalenceItemDto;
import com.caddy.erasxchange.models.course.ExternalCourse;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = CourseFromId.class )
public interface EquivalenceItemMapper {
    @Mapping(source = "bilkentCourseId", target = "bilkentCourse")
    @Mapping(source = "externalCourseId", target = "externalCourse")
    EquivalenceItem toEntity(EquivalenceItemDto equivalenceItemDto);

    @Mapping(source = "externalCourse.id", target = "externalCourseId")
    @Mapping(source = "bilkentCourse.id", target = "bilkentCourseId")
    EquivalenceItemDto toDto(EquivalenceItem equivalenceItem);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    EquivalenceItem partialUpdate(EquivalenceItemDto equivalenceItemDto, @MappingTarget EquivalenceItem equivalenceItem);

    List<EquivalenceItemDto> toDtoList(List<EquivalenceItem> equivalenceItems);

    default Set<Long> bilkentCoursesToIds(Set<BilkentCourse> courses) {
        return courses.stream().map(BilkentCourse::getId).collect(Collectors.toSet());
    }

    default Set<Long> externalCoursesToIds(Set<ExternalCourse> courses) {
        return courses.stream().map(ExternalCourse::getId).collect(Collectors.toSet());
    }


}