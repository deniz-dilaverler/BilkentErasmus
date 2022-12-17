package com.caddy.erasxchange.services.user;

import com.caddy.erasxchange.models.Department;
import com.caddy.erasxchange.models.users.Student;
import com.caddy.erasxchange.repositories.user.StudentRepository;
import com.caddy.erasxchange.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService extends GenericService<Student, StudentRepository> {

    @Autowired
    public StudentService(StudentRepository repository) {
        super(repository);
    }

    @Override
    protected String getClassName() {
        return Student.class.getName();
    }


    public Student findByBilkentId(int bilkentId) {
        Optional<Student> studentOptional = repository.findByBilkentId(bilkentId);
        if (studentOptional.isEmpty()) {
            throw new NoResultException("Student not found with bilkentId -> " + bilkentId);
        } else {
            return studentOptional.get();
        }
    }

    public List<Student> findStudentsByDepartment(Department department) {
        return repository.findAllByDepartment(department);
    }
}
