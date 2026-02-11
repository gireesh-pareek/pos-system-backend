package com.gireesh.payload.dto;

import com.gireesh.domain.PaymentType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    Long orderId;

    private Double totalAmount;

    private LocalDateTime createdAt;

    private Long branchId;

    private Long cashierId;

    @NotNull
    private Long customerId;

    @NotNull
    @Valid
    @Size(min = 1)
    private List<OrderItemDTO> items;

    @NotNull
    private PaymentType paymentType;
}
