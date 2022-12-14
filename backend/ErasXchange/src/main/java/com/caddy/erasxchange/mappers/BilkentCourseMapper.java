package com.caddy.erasxchange.mappers;

import com.caddy.erasxchange.DTOs.BilkentCourseDto;
import com.caddy.erasxchange.DTOs.BilkentCoursePostDto;
import com.caddy.erasxchange.models.course.BilkentCourse;
import com.caddy.erasxchange.models.course.ExternalCourse;
import com.caddy.erasxchange.services.courses.ExternalCourseService;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {CourseFromId.class})
public interface BilkentCourseMapper {

    @Mapping(source = "externalCourseIds", target = "externalCourses")
    BilkentCourse toEntity(BilkentCourseDto bilkentCourseDto);

    @Mapping(expression = "java(coursesToCourseIds(bilkentCourse.getExternalCourses()))", target = "externalCourseIds")
    BilkentCourseDto toDto(BilkentCourse bilkentCourse);

    List<BilkentCourseDto> toDtoList(List<BilkentCourse> bilkentCourses);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BilkentCourse partialUpdate(BilkentCourseDto bilkentCourseDto, @MappingTarget BilkentCourse bilkentCourse);

    default Set<Long> coursesToCourseIds(Set<ExternalCourse> courses) {
        return courses.stream().map(ExternalCourse::getId).collect(Collectors.toSet());
    }

    @Mapping(source = "externalCourseIds", target = "externalCourses")
    BilkentCourse postToEntity(BilkentCoursePostDto bilkentCoursePostDto);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BilkentCourse partialUpdatePost(BilkentCoursePostDto bilkentCoursePostDto, @MappingTarget BilkentCourse bilkentCourse);


}

