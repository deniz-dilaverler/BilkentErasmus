package com.caddy.erasxchange.services.courses;

import com.caddy.erasxchange.DTOs.EquivalenceItemDto;
import com.caddy.erasxchange.mappers.EquivalenceItemMapper;
import com.caddy.erasxchange.models.course.ApprovalStatus;
import com.caddy.erasxchange.models.course.BilkentCourse;
import com.caddy.erasxchange.models.course.EquivalenceItem;
import com.caddy.erasxchange.models.course.ExternalCourse;
import com.caddy.erasxchange.repositories.course.EquivalenceItemRepository;
import com.caddy.erasxchange.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquivalenceService extends GenericService<EquivalenceItem, EquivalenceItemRepository> {

    private final EquivalenceItemMapper mapper;

    @Autowired
    public EquivalenceService(EquivalenceItemRepository repository, EquivalenceItemMapper mapper) {
        super(repository);
        this.mapper = mapper;
    }


    public ApprovalStatus checkApprovalStatus(ExternalCourse externalCourse, BilkentCourse bilkentCourse) {
        for (EquivalenceItem equivalenceItem : externalCourse.getEquivalentCourses()) {
            if (equivalenceItem.getBilkentCourse().getId().equals(bilkentCourse.getId())) {
                return equivalenceItem.getApprovalStatus();
            }
        }
        return null;
    }

    public void approveEquivalence(Long id) {
        EquivalenceItem equivalenceItem = this.findById(id);

        if(equivalenceItem.getApprovalStatus() != ApprovalStatus.PENDING)
            throw new UnsupportedOperationException("The equivalence with id " + id + " isn't pending");

        equivalenceItem.setApprovalStatus(ApprovalStatus.ACCEPTED);
        repository.save(equivalenceItem);

    }

    public void denyEquivalence(Long id ) {
        EquivalenceItem equivalenceItem = this.findById(id);

        if(equivalenceItem.getApprovalStatus() != ApprovalStatus.PENDING)
            throw new UnsupportedOperationException("The equivalence with id " + id + " isn't pending");

        equivalenceItem.setApprovalStatus(ApprovalStatus.DENIED);
        repository.save(equivalenceItem);

    }

    public EquivalenceItemDto getEquivalence(Long id) {
        return mapper.toDto( this.findById(id));
    }

    public List<EquivalenceItemDto> getEquivalencesByStatus(ApprovalStatus approvalStatus) {
        return mapper.toDtoList(repository.findAllByApprovalStatus(approvalStatus));
    }


    @Override
    protected String getClassName() {
        return this.getClass().getName();
    }
}
