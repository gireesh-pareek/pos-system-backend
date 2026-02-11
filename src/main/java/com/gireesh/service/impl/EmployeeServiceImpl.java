package com.gireesh.service.impl;

import com.gireesh.domain.UserRole;
import com.gireesh.mapper.UserMapper;
import com.gireesh.modal.Branch;
import com.gireesh.modal.Store;
import com.gireesh.modal.User;
import com.gireesh.payload.dto.UserDTO;
import com.gireesh.repository.BranchRepository;
import com.gireesh.repository.StoreRepository;
import com.gireesh.repository.UserRepository;
import com.gireesh.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final StoreRepository storeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createStoreEmployee(UserDTO employee, Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("Could not find store with id " + storeId));

        Branch branch;

        if(employee.getRole() == UserRole.ROLE_BRANCH_MANAGER && employee.getBranchId() == null){
            throw new RuntimeException("Branch id is required to create branch manager.");
        }

        branch = branchRepository.findById(employee.getBranchId()).orElseThrow(() -> new RuntimeException("Could not find branch with id " + employee.getBranchId()));

        User user = UserMapper.toEntity(employee);
        user.setStore(store);
        user.setBranch(branch);
        user.setPassword(passwordEncoder.encode(employee.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);

        if(employee.getRole() == UserRole.ROLE_BRANCH_MANAGER) {
            branch.setManager(savedUser);
            branchRepository.save(branch);
        }
        return UserMapper.toDTO(savedUser);
    }

    @Override
    public UserDTO createBranchEmployee(UserDTO employee, Long branchId) {
        Branch branch = branchRepository.findById(branchId).orElseThrow(() -> new RuntimeException("Could not find branch with id " + branchId));

        if(employee.getRole() == UserRole.ROLE_BRANCH_CASHIER || employee.getRole() == UserRole.ROLE_BRANCH_MANAGER){
            User user = UserMapper.toEntity(employee);
            user.setBranch(branch);
            user.setPassword(passwordEncoder.encode(employee.getPassword()));
            return UserMapper.toDTO(userRepository.save(user));
        }

        throw new RuntimeException("Branch role not supported.");

    }

    @Override
    public UserDTO updateEmployee(UserDTO employee, Long employeeId) {
        User existingEmployee = userRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee does not exist with id " + employeeId));

        if(employee.getEmail() != null) existingEmployee.setEmail(employee.getEmail());
        if(employee.getFullName() != null) existingEmployee.setFullName(employee.getFullName());
        if(employee.getRole() != null) existingEmployee.setRole(employee.getRole());
        if(employee.getPassword() != null) existingEmployee.setPassword(passwordEncoder.encode(employee.getPassword()));
        if(employee.getBranchId() != null) {
            Branch branch = branchRepository.findById(employee.getBranchId()).orElseThrow(() -> new RuntimeException("Could not find branch with id " + employee.getBranchId()));
            existingEmployee.setBranch(branch);
        }
        if(employee.getStoreId() != null) {
            Store store = storeRepository.findById(employee.getStoreId()).orElseThrow(() -> new RuntimeException("Could not find store with id " + employee.getStoreId()));
            existingEmployee.setStore(store);
        }
        return UserMapper.toDTO(userRepository.save(existingEmployee));
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        User employee = userRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Could not found employee with id " + employeeId));
        userRepository.delete(employee);
    }

    @Override
    public List<UserDTO> findStoreEmployees(Long storeId, UserRole role) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("Could not find store with id " + storeId));
        return userRepository.findByStore(store).stream()
                .filter(user -> role == null || user.getRole() == role)
                .map(UserMapper::toDTO)
                .toList();
    }


    @Override
    public List<UserDTO> findBranchEmployees(Long branchId, UserRole role) {
        Branch branch = branchRepository.findById(branchId).orElseThrow(() -> new RuntimeException("Could not find branch with id " + branchId));
        return userRepository.findByBranch(branch).stream()
                .filter(user -> role == null || user.getRole() == role)
                .map(UserMapper::toDTO)
                .toList();
    }
}
