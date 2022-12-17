package com.caddy.erasxchange.services.university;

import com.caddy.erasxchange.models.university.University;
import com.caddy.erasxchange.repositories.university.UniversityRepository;
import com.caddy.erasxchange.services.GenericService;


public abstract class UniversityService<Uni extends University, Repository extends UniversityRepository<Uni>>
        extends GenericService<Uni,Repository > {

    protected UniversityService(Repository repository) {
        super(repository);
    }
}
