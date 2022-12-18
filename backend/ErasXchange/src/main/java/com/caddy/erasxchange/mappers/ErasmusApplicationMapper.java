package com.caddy.erasxchange.mappers;

import com.caddy.erasxchange.DTOs.ErasmusApplicationDto;
import com.caddy.erasxchange.DTOs.ErasmusApplicationPostDto;
import com.caddy.erasxchange.models.application.ErasmusApplication;
import com.caddy.erasxchange.services.university.ErasmusUniversityService;
import com.caddy.erasxchange.services.user.StudentService;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring",
        uses =
                {ErasmusUniversityService.class, StudentService.class, ErasmusUniversityMapper.class,})
public interface ErasmusApplicationMapper {
    @Mapping(source = "choice5Id", target = "choice5")
    @Mapping(source = "choice4Id", target = "choice4")
    @Mapping(source = "choice3Id", target = "choice3")
    @Mapping(source = "choice2Id", target = "choice2")
    @Mapping(source = "choice1Id", target = "choice1")
    @Mapping(source = "studentId", target = "student")
    ErasmusApplication postToEntity(ErasmusApplicationPostDto erasmusApplicationPostDto);

    @Mapping(source = "studentId", target = "student")
    ErasmusApplication toEntity(ErasmusApplicationDto erasmusApplicationDto);

    @Mapping(source = "student.id", target = "studentId")
    ErasmusApplicationDto toDto(ErasmusApplication erasmusApplication);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "studentId", target = "student.id")
    ErasmusApplication partialUpdate(ErasmusApplicationDto erasmusApplicationDto, @MappingTarget ErasmusApplication erasmusApplication);

    List<ErasmusApplicationDto> toDtoList(List<ErasmusApplication> erasmusApplicationsList);
    /*
    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ErasmusApplication partialUpdate(ErasmusApplicationPostDto erasmusApplicationPostDto, @MappingTarget ErasmusApplication erasmusApplication);
    */
}
