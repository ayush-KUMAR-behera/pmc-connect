
package com.pmc.service;

import com.pmc.dto.ClassReportRequestDTO;
import com.pmc.dto.ClassReportResponseDTO;

public interface ClassReportService {

    ClassReportResponseDTO createReport(
            ClassReportRequestDTO request,
            Long authenticatedTeacherId);
}
