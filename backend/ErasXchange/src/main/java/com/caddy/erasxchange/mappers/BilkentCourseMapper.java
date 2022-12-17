package com.caddy.erasxchange.mappers;

import com.caddy.erasxchange.DTOs.BilkentCourseDto;
import com.caddy.erasxchange.DTOs.BilkentCoursePostDto;
import com.caddy.erasxchange.models.course.BilkentCourse;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN, componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
        ,uses = {EquivalenceItemMapper.class})

public interface BilkentCourseMapper {
    BilkentCourse toEntity(BilkentCourseDto bilkentCourseDto);

    BilkentCourseDto toDto(BilkentCourse bilkentCourse);
    
    List<BilkentCourseDto> toDtoList(List<BilkentCourse> bilkentCourses);
    BilkentCourse postToEntity(BilkentCoursePostDto bilkentCoursePostDto);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BilkentCourse postPartialUpdate(BilkentCoursePostDto bilkentCoursePostDto, @MappingTarget BilkentCourse bilkentCourse);
}