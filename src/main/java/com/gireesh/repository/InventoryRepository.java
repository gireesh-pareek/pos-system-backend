package com.gireesh.repository;

import com.gireesh.modal.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Inventory findByProductIdAndBranchId(Long productid, Long branchId);
    List<Inventory> findByBranchId(Long branchId);
}
