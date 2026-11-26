
package com.pmc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pmc.dto.ClassReportRequestDTO;
import com.pmc.dto.ClassReportResponseDTO;
import com.pmc.service.ClassReportService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/class-reports")
@RequiredArgsConstructor
public class ClassReportController {

    private final ClassReportService classReportService;

    @PostMapping
    public ResponseEntity<ClassReportResponseDTO> createReport(
            @Valid @RequestBody ClassReportRequestDTO request) {

        // Temporary development placeholder.
        // Replace with the authenticated user's ID when security is implemented.
        Long authenticatedTeacherId = 1L;

        ClassReportResponseDTO createdReport =
                classReportService.createReport(
                        request, authenticatedTeacherId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdReport);
    }
}
