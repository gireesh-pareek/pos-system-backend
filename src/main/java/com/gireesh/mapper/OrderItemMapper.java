package com.gireesh.mapper;

import com.gireesh.modal.OrderItem;
import com.gireesh.payload.dto.OrderItemDTO;

public class OrderItemMapper {

    public static OrderItemDTO toDTO(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }

        return OrderItemDTO.builder()
                .orderItemId(orderItem.getOrderItemId())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getUnitPrice())
                .productId(orderItem.getProduct() != null ? orderItem.getProduct().getId() : null)
                .product(ProductMapper.toDTO(orderItem.getProduct()))
                .orderId(orderItem.getOrder() != null ? orderItem.getOrder().getOrderId() : null)
                .build();
    }

    public static OrderItem toEntity(OrderItemDTO dto) {
        if (dto == null) {
            return null;
        }

        return OrderItem.builder()
                .orderItemId(dto.getOrderItemId())
                .quantity(dto.getQuantity())
                .unitPrice(dto.getPrice())
                .product(ProductMapper.toEntity(dto.getProduct(), null, null))
//                .order()
                .build();
    }
}
