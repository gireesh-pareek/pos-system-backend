package com.gireesh.controller;

import com.gireesh.domain.OrderStatus;
import com.gireesh.domain.PaymentType;
import com.gireesh.payload.dto.OrderDTO;
import com.gireesh.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO){
        return ResponseEntity.ok(orderService.createOrder(orderDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<OrderDTO>> getBranchOrdersWithFilter(
            @PathVariable Long branchId,
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) Long cachierId,
            @RequestParam(required = false) PaymentType paymentType,
            @RequestParam(required = false)OrderStatus orderStatus){
        return ResponseEntity.ok(orderService.getBranchOrdersWithFilter(branchId, customerId, cachierId, paymentType, orderStatus));
    }

    @GetMapping("/cashier/{cashierId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByCashier(@PathVariable Long cashierId){
        return ResponseEntity.ok(orderService.getOrderByCashierId(cashierId));
    }

    @GetMapping("/branch/today/{branchId}")
    public ResponseEntity<List<OrderDTO>> getTodayOrders(@PathVariable Long branchId){
        return ResponseEntity.ok(orderService.getTodayOrdersByBranch(branchId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderDTO>> getCustomerOrders(@PathVariable Long customerId){
        return ResponseEntity.ok(orderService.getOrdersByCustomerId(customerId));
    }

    @GetMapping("/recent/{branchId}")
    public ResponseEntity<List<OrderDTO>> getRecentOrders(@PathVariable Long branchId){
        return ResponseEntity.ok(orderService.get5RecentOrdersByBranch(branchId));
    }
}
