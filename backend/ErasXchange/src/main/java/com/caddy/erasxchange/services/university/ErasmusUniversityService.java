package com.caddy.erasxchange.services.university;

import com.caddy.erasxchange.DTOs.AddErasmusUniversityDto;
import com.caddy.erasxchange.DTOs.ErasmusUniversityDto;
import com.caddy.erasxchange.DTOs.ProgramDto;
import com.caddy.erasxchange.mappers.ErasmusUniversityMapper;
import com.caddy.erasxchange.mappers.ProgramMapper;
import com.caddy.erasxchange.models.university.Program;
import com.caddy.erasxchange.models.university.ErasmusUniversity;
import com.caddy.erasxchange.models.users.Coordinator;
import com.caddy.erasxchange.repositories.university.ErasmusUniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ErasmusUniversityService extends com.caddy.erasxchange.services.GenericService<ErasmusUniversity, ErasmusUniversityRepository> {
    final ProgramService programService;
    final ProgramMapper programMapper;
    final ErasmusUniversityMapper erasmusUniversityMapper;

    @Autowired
    public ErasmusUniversityService(ErasmusUniversityRepository repository, ProgramService programService, ProgramMapper programMapper, ErasmusUniversityMapper erasmusUniversityMapper) {
        super(repository);
        this.programService = programService;
        this.programMapper = programMapper;
        this.erasmusUniversityMapper = erasmusUniversityMapper;
    }

    public void addUniversity(AddErasmusUniversityDto addDto) {
        ErasmusUniversity erasmusUniversity = erasmusUniversityMapper.addToEntity(addDto);

        repository.save(erasmusUniversity);

    }

    public void addProgram(ProgramDto programDto) {
        ErasmusUniversity university = findById(programDto.getUniversityId());
        Program program = programMapper.toEntity(programDto);
        university.getPrograms().add(program);
        program.setUniversity(university);
        programService.saveEtity(program);
    }

    public List<ErasmusUniversityDto> getUniversities() {
        return erasmusUniversityMapper.toDtoList(repository.findAll());
    }

    public ErasmusUniversityDto getUniversity(Long id) {
        return erasmusUniversityMapper.toDto(findById(id));
    }


    @Override
    protected String getClassName() {
        return ErasmusUniversity.class.getName();
    }
}
