
package com.pmc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pmc.entity.TeacherAssignment;

public interface TeacherAssignmentRepository
        extends JpaRepository<TeacherAssignment, Long> {

    List<TeacherAssignment> findByTeacherIdAndActiveTrue(Long teacherId);

    List<TeacherAssignment> findByCenterIdAndActiveTrue(Long centerId);

    boolean existsByTeacherIdAndCenterIdAndActiveTrue(
            Long teacherId, Long centerId);
}
