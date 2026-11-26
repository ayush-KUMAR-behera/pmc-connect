
package com.pmc.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassReportResponseDTO {

    private Long id;

    private Long centerId;
    private String centerName;

    private Long teacherId;
    private String teacherName;

    private LocalDate reportDate;
    private String className;
    private String subject;

    private LocalTime startTime;
    private LocalTime endTime;

    private Integer totalStudents;
    private Integer presentStudents;
    private Integer absentStudents;
    private Double attendancePercentage;

    private String topicsCovered;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
