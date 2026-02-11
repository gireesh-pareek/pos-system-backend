package com.gireesh.controller;

import com.gireesh.domain.UserRole;
import com.gireesh.payload.dto.UserDTO;
import com.gireesh.payload.response.APIResponse;
import com.gireesh.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/store/{storeId}")
    public ResponseEntity<UserDTO> createStoreEmployee(@PathVariable Long storeId, @RequestBody UserDTO employee){
        return ResponseEntity.ok(employeeService.createStoreEmployee(employee, storeId));
    }

    @PostMapping("/branch/{branchId}")
    public ResponseEntity<UserDTO> createBranchEmployee(@PathVariable Long branchId, @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(employeeService.createBranchEmployee(userDTO, branchId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateEmployee(@PathVariable Long id, @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(employeeService.updateEmployee(userDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(new APIResponse("Successfully deleted employee with id " + id));
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<UserDTO>> getStoreEmployees(@PathVariable Long storeId, @RequestParam (required = false) UserRole userRole){
        return ResponseEntity.ok(employeeService.findStoreEmployees(storeId, userRole));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<UserDTO>> getBranchEmployees(@PathVariable Long branchId, @RequestParam (required = false) UserRole userRole){
        return ResponseEntity.ok(employeeService.findBranchEmployees(branchId, userRole));
    }
}
