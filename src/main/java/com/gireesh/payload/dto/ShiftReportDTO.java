package com.gireesh.payload.dto;

import com.gireesh.modal.*;
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
public class ShiftReportDTO {

    private Long id;

    private LocalDateTime shiftStartTime;

    private LocalDateTime shiftEndTime;

    private Double totalSales;

    private Double totalRefunds;

    private Double netSale;

    private Integer totalOrders;

    private Long cashierId;

    private Long branchId;

    private List<PaymentSummary> paymentSummaries;

    private List<ProductDTO> topSellingProducts;

    private List<OrderDTO> recentOrders;

    private List<RefundDTO> refunds;
}
