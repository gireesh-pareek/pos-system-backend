package com.gireesh.service;

import com.gireesh.payload.dto.ShiftReportDTO;

import java.time.LocalDate;
import java.util.List;

public interface ShiftReportService {

    ShiftReportDTO startShift();
    ShiftReportDTO endShift();
    ShiftReportDTO getShiftReportById(Long id);
    List<ShiftReportDTO> getAllShiftReports();
    List<ShiftReportDTO> getShiftReportsByBranchId(Long branchId);
    List<ShiftReportDTO> getShiftReportByCashierId(Long cashierId);
    ShiftReportDTO getCurrentShiftProgress();
    ShiftReportDTO getShiftReportByCashierAndDate(Long cashierId, LocalDate date);
}
