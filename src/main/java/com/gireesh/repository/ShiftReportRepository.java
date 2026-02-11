package com.gireesh.repository;

import com.gireesh.modal.ShiftReport;
import com.gireesh.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ShiftReportRepository extends JpaRepository<ShiftReport, Long> {
    List<ShiftReport> findByCashier_id(Long cashierId);
    List<ShiftReport> findByBranch_id(Long branchId);
    Optional<ShiftReport> findTopByCashierAndShiftEndTimeIsNullOrderByShiftStartTimeDesc(User cashier);
    Optional<ShiftReport> findByCashierAndShiftStartTimeBetween(User cashier, LocalDateTime start, LocalDateTime end);
}
