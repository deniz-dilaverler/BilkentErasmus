package com.caddy.erasxchange.services.university;

import com.caddy.erasxchange.DTOs.BilateralUniversityDto;
import com.caddy.erasxchange.mappers.BilateralUniversityMapper;
import com.caddy.erasxchange.models.university.BilateralUniversity;
import com.caddy.erasxchange.models.users.Coordinator;
import com.caddy.erasxchange.repositories.university.BilateralUniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * handles requests on bilateral Universities
 */
@Service
public class BilateralUniversityService extends  UniversityService<BilateralUniversity, BilateralUniversityRepository> {

    final BilateralUniversityMapper bilateralUniversityMapper;
    @Autowired
    public BilateralUniversityService(BilateralUniversityRepository repository, BilateralUniversityMapper bilateralUniversityMapper) {
        super(repository);
        this.bilateralUniversityMapper = bilateralUniversityMapper;
    }

    /**
     * adds university by mapping the given dto to an entity and persisting it
     * @param bilateralUniversityDto
     */
    public void addUniversity(BilateralUniversityDto bilateralUniversityDto) {
        BilateralUniversity bilateralUniversity = bilateralUniversityMapper.toEntity(bilateralUniversityDto);
        for(Coordinator coordinator : bilateralUniversity.getCoordinators()) {
            coordinator.getResponsibleSchools().add(bilateralUniversity);
        }
        repository.save(bilateralUniversity);
    }

    /**
     * @return all bilateral universities in a list of dtos
     */
    public List<BilateralUniversityDto> getUniversities() {

        return bilateralUniversityMapper.toDtoList(repository.findAll());
    }

    /**
     * @param id bilateral 's  database id
     * @return university in dto form
     */
    public BilateralUniversityDto getUniversity(Long id) {
        return bilateralUniversityMapper.toDto(findById(id));
    }


    @Override
    protected String getClassName() {
        return BilateralUniversity.class.getName();
    }
}
