package com.gireesh.service;

import com.gireesh.domain.OrderStatus;
import com.gireesh.domain.PaymentType;
import com.gireesh.payload.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO updateOrder(Long orderId, OrderDTO orderDTO);
    OrderDTO getOrderById(Long Id);
    List<OrderDTO> getBranchOrdersWithFilter(Long branchId, Long customerId, Long cashierId, PaymentType paymentType, OrderStatus orderStatus);
    List<OrderDTO> getOrderByCashierId(Long cashierId);
    void deleteOrder(Long id);
    List<OrderDTO> getTodayOrdersByBranch(Long branchId);
    List<OrderDTO> getOrdersByCustomerId(Long customerId);
    List<OrderDTO> get5RecentOrdersByBranch(Long branchId);
}
