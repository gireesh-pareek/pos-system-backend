package com.gireesh.service.impl;

import com.gireesh.domain.OrderStatus;
import com.gireesh.domain.PaymentType;
import com.gireesh.mapper.OrderMapper;
import com.gireesh.modal.*;
import com.gireesh.payload.dto.OrderDTO;
import com.gireesh.repository.CustomerRepository;
import com.gireesh.repository.OrderRepository;
import com.gireesh.repository.ProductRepository;
import com.gireesh.service.OrderService;
import com.gireesh.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserService userService;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        User cashier = userService.getCurrentUser();
        Branch branch = cashier.getBranch();
        if(branch == null) throw new EntityNotFoundException("Cashier branch is null");

        Customer customer = customerRepository.findById(orderDTO.getCustomerId()).orElseThrow(() -> new RuntimeException("Can not find customer with id: " + orderDTO.getCustomerId()));

        Order order = Order.builder()
                .branch(branch)
                .cashier(cashier)
                .customer(customer)
                .paymentType(orderDTO.getPaymentType())
                .build();

        orderDTO.getItems().forEach(itemDTO -> {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemDTO.getProductId()));

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(itemDTO.getQuantity())
                    .unitPrice(product.getSellingPrice())
                    .build();

            order.addOrderItem(orderItem);  // Sets both sides of relationship
        });

        order.calculateAndSetTotal();

        Order savedOrder = orderRepository.save(order);

        return OrderMapper.toDTO(savedOrder);
    }

    @Override
    public OrderDTO updateOrder(Long orderId, OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        return OrderMapper.toDTO(
                orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Could not find order with id " + id))
        );
    }

    @Override
    public List<OrderDTO> getBranchOrdersWithFilter(Long branchId, Long customerId, Long cashierId, PaymentType paymentType, OrderStatus orderStatus) {
        return orderRepository.findByBranch_Id(branchId)
                .stream()
                .filter(order ->
                        customerId == null || (order.getCustomer() != null && order.getCustomer().getId().equals(customerId)))
                .filter(order ->
                        cashierId == null || (order.getCashier() != null && order.getCashier().getId().equals(cashierId)))
                .filter(order -> paymentType == null || order.getPaymentType().equals(paymentType))
                .map(OrderMapper::toDTO)
                .toList();
    }

    @Override
    public List<OrderDTO> getOrderByCashierId(Long cashierId) {
        return orderRepository.findByCashier_Id(cashierId)
                .stream()
                .map(OrderMapper::toDTO)
                .toList();
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<OrderDTO> getTodayOrdersByBranch(Long branchId) {
        LocalDate today = LocalDate.now();
        LocalDateTime startTime = today.atStartOfDay();
        LocalDateTime endTime = today.plusDays(1).atStartOfDay();
        return orderRepository.findByBranch_IdAndCreatedAtBetween(branchId, startTime, endTime)
                .stream()
                .map(OrderMapper::toDTO)
                .toList();
    }

    @Override
    public List<OrderDTO> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomer_Id(customerId)
                .stream()
                .map(OrderMapper::toDTO)
                .toList();
    }

    @Override
    public List<OrderDTO> get5RecentOrdersByBranch(Long branchId) {
        return orderRepository.findTop5ByBranch_IdOrderByCreatedAtDesc(branchId)
                .stream()
                .map(OrderMapper::toDTO)
                .toList();
    }
}
