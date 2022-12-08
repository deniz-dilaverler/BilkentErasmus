package com.caddy.erasxchange.services.application;

import com.caddy.erasxchange.DTOs.ApplicationDto;
import com.caddy.erasxchange.DTOs.ApplicationPostDto;
import com.caddy.erasxchange.mappers.ApplicationPostMapper;
import com.caddy.erasxchange.models.application.ErasmusApplication;
import com.caddy.erasxchange.repositories.ApplicationRepository;
import com.caddy.erasxchange.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ErasmusApplicationService extends GenericService<ErasmusApplication, ApplicationRepository<ErasmusApplication>>
                                        implements ApplicationServiceStrategy<ErasmusApplication>{

    private final ApplicationPostMapper applicationPostMapper;

    @Autowired
    public ErasmusApplicationService(ApplicationRepository<ErasmusApplication> applicationRepository,
                                     ApplicationPostMapper applicationPostMapper) {
        super(applicationRepository);
        this.applicationPostMapper = applicationPostMapper;

    }

    /*
    convert post dto to application and persist it
     */
    @Override
    public void addApplication(ApplicationPostDto applicationPostDTO) {
        addEtity(applicationPostMapper.applicationPostToErasmusApplicationMapper(applicationPostDTO));
    }

    @Override
    public ApplicationDto getById(Long id) {
        return null;
    }

    @Override
    public void updateApp(ApplicationDto applicationDto) {

    }

    @Override
    public List<ApplicationDto> getAll() {
        return null;
    }


}
