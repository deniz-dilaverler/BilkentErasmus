package com.caddy.erasxchange.services.university;

import com.caddy.erasxchange.DTOs.AddErasmusUniversityDto;
import com.caddy.erasxchange.DTOs.ErasmusUniversityDto;
import com.caddy.erasxchange.DTOs.ProgramDto;
import com.caddy.erasxchange.mappers.ErasmusUniversityMapper;
import com.caddy.erasxchange.mappers.ProgramMapper;
import com.caddy.erasxchange.models.university.Program;
import com.caddy.erasxchange.models.university.ErasmusUniversity;
import com.caddy.erasxchange.models.users.Coordinator;
import com.caddy.erasxchange.repositories.ProgramRepository;
import com.caddy.erasxchange.repositories.university.ErasmusUniversityRepository;
import com.caddy.erasxchange.services.user.CoordinatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * handles request for ErasmusUniversity operations
 */
@Service
public class ErasmusUniversityService extends com.caddy.erasxchange.services.GenericService<ErasmusUniversity, ErasmusUniversityRepository> {

    final ProgramMapper programMapper;
    final ErasmusUniversityMapper erasmusUniversityMapper;
    final private ProgramRepository programRepository;
    final private CoordinatorService coordinatorService;

    @Autowired
    public ErasmusUniversityService(ErasmusUniversityRepository repository, ProgramMapper programMapper, ErasmusUniversityMapper erasmusUniversityMapper, ProgramRepository programRepository,
                                    ErasmusUniversityRepository erasmusUniversityRepository, CoordinatorService coordinatorService) {
        super(repository);

        this.programMapper = programMapper;
        this.erasmusUniversityMapper = erasmusUniversityMapper;
        this.programRepository = programRepository;
        this.coordinatorService = coordinatorService;
    }

    /**
     * adds the ErasmusUniversity by mapping the given university to an entity
     * @param addDto given by the request
     */
    public void addUniversity(AddErasmusUniversityDto addDto) {
        System.out.println(addDto);
        ErasmusUniversity erasmusUniversity = erasmusUniversityMapper.addToEntity(addDto);
        ErasmusUniversity erasUni = repository.findByName(erasmusUniversity.getName());
        Coordinator coordinator = coordinatorService.findById(addDto.getCoordinatorId());
        // create program with the coordinator's department
        Program program = new Program().setDepartment(coordinator.getDepartment()).setQuota(addDto.getQuota());
        if (erasUni != null)
            erasmusUniversity = erasUni;

        erasmusUniversity.getPrograms().add(program);
        program.setUniversity(erasmusUniversity);
//
        coordinator.getResponsibleSchools().add(erasmusUniversity);
        erasmusUniversity.getCoordinators().add(coordinator);

        coordinatorService.saveEtity(coordinator);
        repository.save(erasmusUniversity);
    }

    /**
     * adds the given program to the university
     * @param programDto
     */
    public void addProgram(ProgramDto programDto) {
        ErasmusUniversity university = findById(programDto.getUniversityId());
        Program program = programMapper.toEntity(programDto);
        university.getPrograms().add(program);
        program.setUniversity(university);
        programRepository.save(program);
        repository.save(university);

    }

    /**
     * @return list of Erasmus Universities in dto form
     */
    public List<ErasmusUniversityDto> getUniversities() {
        return erasmusUniversityMapper.toDtoList(repository.findAll());
    }

    /**
     * @param id university 's  database id
     * @return university in dto form
     */
    public ErasmusUniversityDto getUniversity(Long id) {
        return erasmusUniversityMapper.toDto(findById(id));
    }


    @Override
    protected String getClassName() {
        return ErasmusUniversity.class.getName();
    }
}
