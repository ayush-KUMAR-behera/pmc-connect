
package com.pmc.service.IMPL;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pmc.dto.ClassReportRequestDTO;
import com.pmc.dto.ClassReportResponseDTO;
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
import com.pmc.service.ClassReportService;

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
    public ClassReportResponseDTO createReport(
            ClassReportRequestDTO request,
            Long authenticatedTeacherId) {

        // 1. Validate that the centre exists and is active.
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

        // 5. Validate business rules involving multiple fields.
        if (request.getPresentStudents() > request.getTotalStudents()) {
            throw new IllegalArgumentException(
                    "Present students cannot exceed total students");
        }

        if (!request.getEndTime().isAfter(request.getStartTime())) {
            throw new IllegalArgumentException(
                    "End time must be after start time");
        }

        // 6. Map request data to the entity.
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

        // 7. Save and map to a safe API response.
        ClassReport savedReport = classReportRepository.save(report);

        return toResponseDTO(savedReport);
    }

    // Converts the internal entity to the API response DTO.
    private ClassReportResponseDTO toResponseDTO(ClassReport report) {

        int totalStudents = report.getTotalStudents();
        int presentStudents = report.getPresentStudents();

        double attendancePercentage =
                (presentStudents * 100.0) / totalStudents;

        return ClassReportResponseDTO.builder()
                .id(report.getId())
                .centerId(report.getCenter().getId())
                .centerName(report.getCenter().getName())
                .teacherId(report.getTeacher().getId())
                .teacherName(report.getTeacher().getFullName())
                .reportDate(report.getReportDate())
                .className(report.getClassName())
                .subject(report.getSubject())
                .startTime(report.getStartTime())
                .endTime(report.getEndTime())
                .totalStudents(totalStudents)
                .presentStudents(presentStudents)
                .absentStudents(totalStudents - presentStudents)
                .attendancePercentage(
                        Math.round(attendancePercentage * 100.0) / 100.0)
                .topicsCovered(report.getTopicsCovered())
                .createdAt(report.getCreatedAt())
                .updatedAt(report.getUpdatedAt())
                .build();
    }
}
