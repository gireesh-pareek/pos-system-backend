package com.gireesh.repository;

import com.gireesh.modal.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomer_Id(Long customerId);
    List<Order> findByBranch_Id(Long branchId);
    List<Order> findByCashier_Id(Long cashierId);
    List<Order> findByBranch_IdAndCreatedAtBetween(Long BranchId, LocalDateTime from, LocalDateTime to);
    List<Order> findByCashier_IdAndCreatedAtBetween(Long cashierId, LocalDateTime from, LocalDateTime to);
    List<Order> findTop5ByBranch_IdOrderByCreatedAtDesc(Long branchId);
}
