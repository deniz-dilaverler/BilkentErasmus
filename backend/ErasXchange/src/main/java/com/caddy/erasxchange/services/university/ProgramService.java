package com.caddy.erasxchange.services.university;

import com.caddy.erasxchange.models.university.Program;
import com.caddy.erasxchange.repositories.ProgramRepository;
import com.caddy.erasxchange.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProgramService extends GenericService<Program, ProgramRepository> {
    @Autowired
    public ProgramService(ProgramRepository repository) {
        super(repository);
    }

    @Override
    protected String getClassName() {
        return Program.class.getName();
    }
}
