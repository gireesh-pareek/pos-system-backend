package com.gireesh.controller;

import com.gireesh.payload.dto.RefundDTO;
import com.gireesh.service.RefundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/refunds")
public class RefundController {
    private final RefundService refundService;

    @PostMapping
    public ResponseEntity<RefundDTO> createRefund(@Valid @RequestBody RefundDTO refundDTO){
        return ResponseEntity.ok(refundService.createRefund(refundDTO));
    }

    @GetMapping
    public ResponseEntity<List<RefundDTO>> getAllRefunds(){
        return ResponseEntity.ok(refundService.getAllRefunds());
    }

    @GetMapping("/cashier/{cashierId}")
    public ResponseEntity<List<RefundDTO>> getRefundsByCashier(@PathVariable Long cashierId){
        return ResponseEntity.ok(refundService.getRefundByCashier(cashierId));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<RefundDTO>> getRefundsByBranch(@PathVariable Long branchId){
        return ResponseEntity.ok(refundService.getRefundByBranch(branchId));
    }

    @GetMapping("/shift/{shiftReportId}")
    public ResponseEntity<List<RefundDTO>> getRefundsByShift(@PathVariable Long shiftReportId){
        return ResponseEntity.ok(refundService.getRefundByShiftReport(shiftReportId));
    }

    @GetMapping("/cashier/{cashierId}/range")
    public ResponseEntity<List<RefundDTO>> getRefundsByCashierAndDateRange(@PathVariable Long cashierId,
                                                               @RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                               @RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate)
    {
        return ResponseEntity.ok(refundService.getRefundByCashierAndDateRange(cashierId, startDate, endDate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefundDTO> getRefundById(@PathVariable Long id){
        return ResponseEntity.ok(refundService.getRefundById(id));
    }
}
