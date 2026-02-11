package com.gireesh.service;

import com.gireesh.domain.UserRole;
import com.gireesh.payload.dto.UserDTO;

import java.util.List;

public interface EmployeeService {

    UserDTO createStoreEmployee(UserDTO employee, Long storeId);
    UserDTO createBranchEmployee(UserDTO employee, Long branchId);
    UserDTO updateEmployee(UserDTO employee, Long employeeId);
    void deleteEmployee(Long employeeId);
    List<UserDTO> findStoreEmployees(Long storeId, UserRole role);
    List<UserDTO> findBranchEmployees(Long branchId, UserRole role);

}
