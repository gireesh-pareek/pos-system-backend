package com.gireesh.controller;

import com.gireesh.payload.dto.ShiftReportDTO;
import com.gireesh.service.ShiftReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/shift-reports")
public class ShiftReportController {

    private final ShiftReportService shiftReportService;

    @PostMapping("/start")
    public ResponseEntity<ShiftReportDTO> startShift(){
        return ResponseEntity.ok(shiftReportService.startShift());
    }

    @PatchMapping("/end")
    public ResponseEntity<ShiftReportDTO> endShift(){
        return ResponseEntity.ok(shiftReportService.endShift());
    }

    @GetMapping("/current")
    public ResponseEntity<ShiftReportDTO> getCurrentShiftProgress(){
        return ResponseEntity.ok(shiftReportService.getCurrentShiftProgress());
    }

    @GetMapping("/cashier/{cashierId}/by-date")
    public ResponseEntity<ShiftReportDTO> getCashierShiftReportByDate(@PathVariable Long cashierId,
                                                                      @RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE) LocalDate date
    ){
        return ResponseEntity.ok(shiftReportService.getShiftReportByCashierAndDate(cashierId, date));
    }

    @GetMapping("/cashier/{cashierId}")
    public ResponseEntity<List<ShiftReportDTO>> getShiftReportByCashier(@PathVariable Long cashierId){
        return ResponseEntity.ok(shiftReportService.getShiftReportByCashierId(cashierId));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<ShiftReportDTO>> getShiftReportByBranch(@PathVariable Long branchId){
        return ResponseEntity.ok(shiftReportService.getShiftReportsByBranchId(branchId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShiftReportDTO> getShiftReportById(@PathVariable Long id){
        return ResponseEntity.ok(shiftReportService.getShiftReportById(id));
    }

}
