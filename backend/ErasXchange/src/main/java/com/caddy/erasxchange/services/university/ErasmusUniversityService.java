package com.caddy.erasxchange.services.university;

import com.caddy.erasxchange.DTOs.AddErasmusUniversityDto;
import com.caddy.erasxchange.DTOs.ErasmusUniversityDto;
import com.caddy.erasxchange.DTOs.ProgramDto;
import com.caddy.erasxchange.mappers.ErasmusUniversityMapper;
import com.caddy.erasxchange.mappers.ProgramMapper;
import com.caddy.erasxchange.models.university.Program;
import com.caddy.erasxchange.models.university.ErasmusUniversity;
import com.caddy.erasxchange.repositories.ProgramRepository;
import com.caddy.erasxchange.repositories.university.ErasmusUniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ErasmusUniversityService extends com.caddy.erasxchange.services.GenericService<ErasmusUniversity, ErasmusUniversityRepository> {

    final ProgramMapper programMapper;
    final ErasmusUniversityMapper erasmusUniversityMapper;
    final private ProgramRepository programRepository;
    @Autowired
    public ErasmusUniversityService(ErasmusUniversityRepository repository, ProgramMapper programMapper, ErasmusUniversityMapper erasmusUniversityMapper, ProgramRepository programRepository) {
        super(repository);

        this.programMapper = programMapper;
        this.erasmusUniversityMapper = erasmusUniversityMapper;
        this.programRepository = programRepository;
    }

    public void addUniversity(AddErasmusUniversityDto addDto) {
        ErasmusUniversity erasmusUniversity = erasmusUniversityMapper.addToEntity(addDto);

        for(Program program : erasmusUniversity.getPrograms()) {
            program.setId(null);
            program.setUniversity(erasmusUniversity);
        }


        repository.save(erasmusUniversity);
    }

    public void addProgram(ProgramDto programDto) {
        ErasmusUniversity university = findById(programDto.getUniversityId());
        Program program = programMapper.toEntity(programDto);
        university.getPrograms().add(program);
        program.setUniversity(university);
        programRepository.save(program);
        repository.save(university);

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
