package com.caddy.erasxchange.services.courses;

import com.caddy.erasxchange.DTOs.ExternalCourseDto;
import com.caddy.erasxchange.DTOs.ExternalCoursePostDto;
import com.caddy.erasxchange.mappers.ExternalCourseMapper;
import com.caddy.erasxchange.models.course.ApprovalStatus;
import com.caddy.erasxchange.models.course.BilkentCourse;
import com.caddy.erasxchange.models.course.ExternalCourse;
import com.caddy.erasxchange.models.university.BilateralUniversity;
import com.caddy.erasxchange.models.university.ErasmusUniversity;
import com.caddy.erasxchange.repositories.course.ExternalCourseRepository;
import com.caddy.erasxchange.repositories.university.BilateralUniversityRepository;
import com.caddy.erasxchange.repositories.university.ErasmusUniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExternalCourseService extends CourseService<ExternalCourse, ExternalCourseRepository> {

    private final ExternalCourseMapper courseMapper;

    private final ErasmusUniversityRepository erasmusUniversityRepository;
    private final BilateralUniversityRepository bilateralUniversityRepository;
    private final BilkentCourseService bilkentCourseService;

    @Autowired
    public ExternalCourseService(ExternalCourseRepository repository, ExternalCourseMapper courseMapper, ErasmusUniversityRepository erasmusUniversityRepository, BilateralUniversityRepository bilateralUniversityRepository, BilkentCourseService bilkentCourseService) {
        super(repository);
        this.courseMapper = courseMapper;
        this.erasmusUniversityRepository = erasmusUniversityRepository;
        this.bilateralUniversityRepository = bilateralUniversityRepository;
        this.bilkentCourseService = bilkentCourseService;
    }


    @Override
    protected String getClassName() {
        return ExternalCourse.class.getName();
    }


    public ExternalCourseDto getCourse(Long id) {
        return courseMapper.toDto(super.findById(id));
    }

    public List<ExternalCourseDto> getCourses() {
        return courseMapper.toDtoList(super.findAll());
    }

    public void addCourse(ExternalCoursePostDto externalCoursePostDto) {
        if (externalCoursePostDto.getEquivalentCourseIds().size() > 0)
            throw new IllegalArgumentException("ExternalCoursePostDto can't have equivalent courses during addition to the server");

        ExternalCourse newCourse = courseMapper.postToEntity(externalCoursePostDto);
        newCourse.setApprovalStatus(ApprovalStatus.PENDING);

        repository.save(newCourse);

    }

    // this is only for admins and testing
    public void addCourseDirectly(ExternalCourseDto courseDto) {
        if (courseDto.getId() != null)
            throw new IllegalArgumentException("The id field must be empty for ExternalCourse to be added");

        ExternalCourse externalCourse = courseMapper.toEntity(courseDto);
        this.setUniversity(externalCourse);

        repository.save(externalCourse);

    }

    // only for admins and testing
    public void addEquivalenceDirectly(Long externalCourseId, Long equivalentCourseId) {
        ExternalCourse externalCourse = this.findById(externalCourseId);
        BilkentCourse bilkentCourse = bilkentCourseService.findById(equivalentCourseId);

        externalCourse.getEquivalentCourses().add(bilkentCourse);
        bilkentCourse.getExternalCourses().add(externalCourse);
        repository.save(externalCourse);
    }

    /*
        mappers can't bind ErasmusUniversity or BilateralUniversity to ExternalCourse class so it creates a university
        instance with just the id, this method uses that id and manyally assigns the university object
     */
    private void setUniversity(ExternalCourse externalCourse) {
        if (externalCourse.getIsErasmus()) {
            Optional<ErasmusUniversity> optional = erasmusUniversityRepository.findById(externalCourse.getUniversity().getId());

            if (optional.isEmpty()) throw new ResourceNotFoundException("ErasmusUniversity with id: " + externalCourse.getUniversity().getId() + ",  not found in " + this.getClassName()) ;
            else  externalCourse.setUniversity(optional.get());
        }
        else {
            Optional<BilateralUniversity> optional = bilateralUniversityRepository.findById(externalCourse.getUniversity().getId());

            if (optional.isEmpty()) throw new ResourceNotFoundException("ErasmusUniversity with id: " + externalCourse.getUniversity().getId() + ",  not found in " + this.getClassName()) ;
            else  externalCourse.setUniversity(optional.get());
        }
        // set bidirectional relation
        externalCourse.getUniversity().getCourses().add(externalCourse);
    }
}
