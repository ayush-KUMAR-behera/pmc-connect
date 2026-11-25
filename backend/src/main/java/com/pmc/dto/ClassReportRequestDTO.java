package com.pmc.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassReportRequestDTO {

    @NotNull(message = "Center ID is required")
    @Positive(message = "Center ID must be a positive number")
    private Long centerId;

    @NotNull(message = "Report date is required")
    @PastOrPresent(message = "Report date cannot be in the future")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reportDate;

    @NotBlank(message = "Class name is required")
    @Size(max =50, message = "Class name must not exceed 50 characters")
    private String className;

    @NotBlank(message = "Subject is required")
    @Size(max = 100, message = "Subject must not exceed 100 characters")
    private String subject;

    @NotNull(message = "Start time is required")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @NotNull(message = "Total students count is required")
    @Min(value = 1, message = "Total students must be at least 1")
    private Integer totalStudents;

    @NotNull(message = "Present students count is required")
    @Min(value = 0, message = "Present students cannot be negative")
    private Integer presentStudents;

    @NotBlank(message = "Topics covered is required")
    @Size(max = 2000, message = "Topics covered must not exceed 2000 characters")
    private String topicsCovered;

   
}
