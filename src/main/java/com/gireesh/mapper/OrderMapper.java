package com.gireesh.mapper;

import com.gireesh.modal.*;
import com.gireesh.payload.dto.OrderDTO;
import com.gireesh.payload.dto.OrderItemDTO;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDTO toDTO(Order order) {
        if (order == null) {
            return null;
        }

        return OrderDTO.builder()
                .orderId(order.getOrderId())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .paymentType(order.getPaymentType())

                .branchId(order.getBranch() != null ? order.getBranch().getId() : null)

                .customerId(order.getCustomer() != null ? order.getCustomer().getId() : null)

                .items(mapItemsToDTO(order.getItems()))
                .build();
    }

    public static Order toEntity(OrderDTO dto) {
        if (dto == null) {
            return null;
        }

        return Order.builder()
                .orderId(dto.getOrderId())
                .totalAmount(dto.getTotalAmount())
                .createdAt(dto.getCreatedAt())
                .paymentType(dto.getPaymentType())
                .items(mapItemsToEntity(dto.getItems()))
                .build();
    }

    private static List<OrderItemDTO> mapItemsToDTO(List<OrderItem> items) {
        if (items == null) {
            return null;
        }
        return items.stream()
                .map(OrderItemMapper::toDTO)
                .collect(Collectors.toList());
    }

    private static List<OrderItem> mapItemsToEntity(List<OrderItemDTO> items) {
        if (items == null) {
            return null;
        }
        return items.stream()
                .map(OrderItemMapper::toEntity)
                .collect(Collectors.toList());
    }
}
