package com.caddy.erasxchange.mappers;

import com.caddy.erasxchange.DTOs.ErasmusUniversityDto;
import com.caddy.erasxchange.DTOs.ExternalCourseDto;
import com.caddy.erasxchange.DTOs.ExternalCoursePostDto;
import com.caddy.erasxchange.models.course.BilkentCourse;
import com.caddy.erasxchange.models.course.ExternalCourse;
import com.caddy.erasxchange.models.university.BilateralUniversity;
import com.caddy.erasxchange.models.university.ErasmusUniversity;
import com.caddy.erasxchange.models.university.University;
import com.caddy.erasxchange.services.courses.BilkentCourseService;
import com.caddy.erasxchange.services.university.BilateralUniversityService;
import com.caddy.erasxchange.services.university.ErasmusUniversityService;
import com.caddy.erasxchange.services.university.UniversityService;
import org.aspectj.lang.annotation.After;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {CourseFromId.class,
        ErasmusUniversityService.class, BilateralUniversityService.class})
public interface ExternalCourseMapper {


    /*
        University object within the ExternalCourse is only binded by id value, must bind the university using the UniversityServices
     */
    @Mapping(expression = "java(idToUniversity(externalCourseDto))", target = "university")
    @Mapping(source = "equivalentCourseIds", target = "equivalentCourses")
    ExternalCourse toEntity(ExternalCourseDto externalCourseDto);

    @Mapping(target = "equivalentCourseIds", expression = "java(coursesToCourseIds(externalCourse.getEquivalentCourses()))")
    @Mapping(source = "university.id", target = "universityId")
    ExternalCourseDto toDto(ExternalCourse externalCourse);

    ErasmusUniversity idToErasmusUniversity(Long id);

    List<ExternalCourseDto> toDtoList(List<ExternalCourse> externalCourses );

    BilateralUniversity idToBilateralUniversity(Long id);

    default Set<Long> coursesToCourseIds(Set<BilkentCourse> courses) {
        return courses.stream().map(BilkentCourse::getId).collect(Collectors.toSet());
    }


    default University idToUniversity(ExternalCourseDto externalCourseDto) {
        if (externalCourseDto.getIsErasmus()) {
            return idToErasmusUniversity(externalCourseDto.getUniversityId());
        } else {
            return idToBilateralUniversity(externalCourseDto.getUniversityId());
        }
    }
    default University idToUniversity(ExternalCoursePostDto externalCoursePostDto) {
        if (externalCoursePostDto.getIsErasmus()) {
            return idToErasmusUniversity(externalCoursePostDto.getUniversityId());
        } else {
            return idToBilateralUniversity(externalCoursePostDto.getUniversityId());
        }
    }


    /*
        University object within the ExternalCourse is only binded by id value, must bind the university using the UniversityServices
     */
    @Mapping(source = "equivalentCourseIds", target = "equivalentCourses")
    @Mapping(expression = "java(idToUniversity(externalCoursePostDto))", target = "university")
    ExternalCourse postToEntity(ExternalCoursePostDto externalCoursePostDto);








}