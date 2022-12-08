package com.caddy.erasxchange.services.user;

import com.caddy.erasxchange.models.users.Student;
import com.caddy.erasxchange.repositories.user.StudentRepository;
import com.caddy.erasxchange.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService extends GenericService<Student, StudentRepository> {

    @Autowired
    public StudentService(StudentRepository repository) {
        super(repository);
    }


    //needs to be overridden to be able to be seen by mapstruct
    @Override
    public Student findById(Long id) {
        return super.findById(id);
    }
}
