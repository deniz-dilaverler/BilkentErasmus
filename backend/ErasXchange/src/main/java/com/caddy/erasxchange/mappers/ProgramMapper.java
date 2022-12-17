package com.caddy.erasxchange.mappers;

import com.caddy.erasxchange.DTOs.ProgramDto;
import com.caddy.erasxchange.models.university.Program;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ProgramMapper {
    @Mapping(target = "id", ignore = true)
    Program toEntity(ProgramDto programDto);

    ProgramDto toDto(Program program);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Program partialUpdate(ProgramDto programDto, @MappingTarget Program program);
}