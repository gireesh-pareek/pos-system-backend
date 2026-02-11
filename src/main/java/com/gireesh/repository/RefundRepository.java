package com.gireesh.repository;

import com.gireesh.modal.Refund;
import com.gireesh.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RefundRepository extends JpaRepository<Refund, Long> {

    List<Refund> findByCashier_IdAndCreatedAtBetween(Long cashierId, LocalDateTime from, LocalDateTime to);
    List<Refund> findByCashierId(Long cashierId);
    List<Refund> findByShiftReportId(Long id);
    List<Refund> findByBranchId(Long id);
}
