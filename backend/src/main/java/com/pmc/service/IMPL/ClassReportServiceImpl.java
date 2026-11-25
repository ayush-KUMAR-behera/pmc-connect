
package com.pmc.service.IMPL;

import com.pmc.service.*;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pmc.dto.ClassReportRequestDTO;
import com.pmc.entity.Center;
import com.pmc.entity.ClassReport;
import com.pmc.entity.Role;
import com.pmc.entity.User;
import com.pmc.entity.UserStatus;
import com.pmc.exception.ResourceNotFoundException;
import com.pmc.repository.CenterRepository;
import com.pmc.repository.ClassReportRepository;
import com.pmc.repository.TeacherAssignmentRepository;
import com.pmc.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClassReportServiceImpl implements ClassReportService {

    private final ClassReportRepository classReportRepository;
    private final CenterRepository centerRepository;
    private final UserRepository userRepository;
    private final TeacherAssignmentRepository teacherAssignmentRepository;

    @Override
    @Transactional
    public ClassReport createReport(
            ClassReportRequestDTO request,
            Long authenticatedTeacherId) {

        // 1. Validate the centre exists and is active.
        Center center = centerRepository.findById(request.getCenterId())
                .filter(Center::isActive)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Active centre not found with ID: "
                                + request.getCenterId()));

        // 2. Load the teacher.
        User teacher = userRepository.findById(authenticatedTeacherId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Teacher not found with ID: "
                                + authenticatedTeacherId));

        // 3. Only approved teachers may submit reports.
        if (teacher.getRole() != Role.TEACHER
                || teacher.getStatus() != UserStatus.APPROVED) {
            throw new AccessDeniedException(
                    "Only approved teachers can create class reports");
        }

        // 4. Verify the teacher has an active assignment to this centre.
        boolean assigned = teacherAssignmentRepository
                .existsByTeacherIdAndCenterIdAndActiveTrue(
                        authenticatedTeacherId,
                        request.getCenterId());

        if (!assigned) {
            throw new AccessDeniedException(
                    "You are not assigned to this centre");
        }

        // 5. Enforce business rules that involve multiple fields.
        if (request.getPresentStudents() > request.getTotalStudents()) {
            throw new IllegalArgumentException(
                    "Present students cannot exceed total students");
        }

        if (!request.getEndTime().isAfter(request.getStartTime())) {
            throw new IllegalArgumentException(
                    "End time must be after start time");
        }

        // 6. Map validated request data to the entity.
        ClassReport report = new ClassReport();

        report.setTeacher(teacher);
        report.setCenter(center);
        report.setReportDate(request.getReportDate());
        report.setClassName(request.getClassName().trim());
        report.setSubject(request.getSubject().trim());
        report.setStartTime(request.getStartTime());
        report.setEndTime(request.getEndTime());
        report.setTotalStudents(request.getTotalStudents());
        report.setPresentStudents(request.getPresentStudents());
        report.setTopicsCovered(request.getTopicsCovered().trim());

        // 7. Persist the report.
        return classReportRepository.save(report);
    }
}
