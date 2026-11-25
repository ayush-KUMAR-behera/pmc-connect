package com.pmc.service;

import com.pmc.dto.ClassReportRequestDTO;
import com.pmc.entity.ClassReport;

public interface ClassReportService {

	ClassReport createReport(ClassReportRequestDTO request,
							  Long authenticatedTeacherId);
}
