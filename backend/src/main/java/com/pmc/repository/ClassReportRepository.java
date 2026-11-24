
package com.pmc.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pmc.entity.ClassReport;

public interface ClassReportRepository
        extends JpaRepository<ClassReport, Long> {

    List<ClassReport> findByTeacherId(Long teacherId);

    List<ClassReport> findByCenterId(Long centerId);

    List<ClassReport> findByReportDate(LocalDate reportDate);

    List<ClassReport> findByTeacherIdAndReportDate(
            Long teacherId, LocalDate reportDate);

    List<ClassReport> findByCenterIdAndReportDateBetween(
            Long centerId, LocalDate startDate, LocalDate endDate);
}
