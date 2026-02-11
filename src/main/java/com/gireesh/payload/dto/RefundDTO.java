package com.gireesh.payload.dto;

import com.gireesh.domain.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefundDTO {
    private Long id;

    private Long orderId;

    private String reason;

    private Double amount;

    private Long shiftReportId;

    private Long cashierId;

    private Long branchId;

    private PaymentType paymentType;

    private LocalDateTime createdAt;
}
