package com.gireesh.repository;

import com.gireesh.modal.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    List<Branch> findByStoreId(Long branchId);

    Long id(Long id);
}
