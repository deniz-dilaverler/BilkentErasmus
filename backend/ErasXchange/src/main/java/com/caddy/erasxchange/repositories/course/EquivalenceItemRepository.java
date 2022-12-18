package com.caddy.erasxchange.repositories.course;

import com.caddy.erasxchange.models.course.ApprovalStatus;
import com.caddy.erasxchange.models.course.BilkentCourse;
import com.caddy.erasxchange.models.course.EquivalenceItem;
import com.caddy.erasxchange.models.course.ExternalCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquivalenceItemRepository extends JpaRepository<EquivalenceItem, Long> {

    public List<EquivalenceItem> findAllByApprovalStatus(ApprovalStatus approvalStatus);
    EquivalenceItem findByExternalCourseAndBilkentCourse(ExternalCourse course, BilkentCourse bilkentCourse);
}