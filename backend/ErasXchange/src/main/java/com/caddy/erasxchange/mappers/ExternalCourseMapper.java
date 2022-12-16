package com.caddy.erasxchange.mappers;

import com.caddy.erasxchange.DTOs.ExternalCourseDto;
import com.caddy.erasxchange.DTOs.ExternalCoursePostDto;
import com.caddy.erasxchange.models.course.ExternalCourse;
import com.caddy.erasxchange.models.university.BilateralUniversity;
import com.caddy.erasxchange.models.university.ErasmusUniversity;
import com.caddy.erasxchange.models.university.University;
import com.caddy.erasxchange.services.university.BilateralUniversityService;
import com.caddy.erasxchange.services.university.ErasmusUniversityService;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN, componentModel = "spring",
        uses = {EquivalenceItemMapper.class, ErasmusUniversityService.class, BilateralUniversityService.class})
public interface ExternalCourseMapper {

    /*
       University object within the ExternalCourse is only binded by id value, must bind the university using the UniversityServices
    */
    @Mapping(expression = "java(idToUniversity(externalCourseDto))", target = "university")
    ExternalCourse toEntity(ExternalCourseDto externalCourseDto);

    @Mapping(source = "university.id", target = "universityId")
    ExternalCourseDto toDto(ExternalCourse externalCourse);


    @AfterMapping
    default void linkEquivalentCourses(@MappingTarget ExternalCourse externalCourse) {
        externalCourse.getEquivalentCourses().forEach(equivalentCourse -> equivalentCourse.setExternalCourse(externalCourse));
    }
    @Mapping(expression = "java(idToUniversity(externalCoursePostDto))", target = "university")
    ExternalCourse postToEntity(ExternalCoursePostDto externalCoursePostDto);

    List<ExternalCourseDto> toDtoList(List<ExternalCourse> externalCourseList);




    ErasmusUniversity idToErasmusUniversity(Long id);
    BilateralUniversity idToBilateralUniversity(Long id);


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

}