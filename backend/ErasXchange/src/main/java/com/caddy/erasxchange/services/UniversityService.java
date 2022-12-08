package com.caddy.erasxchange.services;

import com.caddy.erasxchange.models.University;
import com.caddy.erasxchange.repositories.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UniversityService extends GenericService<University, UniversityRepository>{

    @Autowired
    public UniversityService(UniversityRepository repository) {
        super(repository);
    }
}
