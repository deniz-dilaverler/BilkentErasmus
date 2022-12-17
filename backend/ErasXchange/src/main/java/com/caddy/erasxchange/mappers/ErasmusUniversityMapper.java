package com.caddy.erasxchange.mappers;

import com.caddy.erasxchange.DTOs.AddErasmusUniversityDto;
import com.caddy.erasxchange.models.university.ErasmusUniversity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ErasmusUniversityMapper {
    ErasmusUniversity toEntity(AddErasmusUniversityDto addErasmusUniversityDto);

    AddErasmusUniversityDto toDto(ErasmusUniversity erasmusUniversity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ErasmusUniversity partialUpdate(AddErasmusUniversityDto addErasmusUniversityDto, @MappingTarget ErasmusUniversity erasmusUniversity);
}