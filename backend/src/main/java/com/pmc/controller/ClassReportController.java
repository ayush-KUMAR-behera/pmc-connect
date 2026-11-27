
package com.pmc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pmc.dto.ClassReportRequestDTO;
import com.pmc.dto.ClassReportResponseDTO;
import com.pmc.entity.User;
import com.pmc.repository.UserRepository;
import com.pmc.service.ClassReportService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/class-reports")
@RequiredArgsConstructor
public class ClassReportController {

    private final ClassReportService classReportService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<ClassReportResponseDTO> createReport(
            @Valid @RequestBody ClassReportRequestDTO request,
            Authentication authentication) {

        User authenticatedUser = userRepository
                .findByEmailIgnoreCase(authentication.getName())
                .orElseThrow(() -> new org.springframework.security.core.userdetails.UsernameNotFoundException(
                        "Authenticated user not found"));

        Long authenticatedTeacherId = authenticatedUser.getId();

        ClassReportResponseDTO createdReport =
                classReportService.createReport(request, authenticatedTeacherId);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdReport);
    }
}
