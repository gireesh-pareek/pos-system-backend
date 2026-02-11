package com.gireesh.mapper;

import com.gireesh.modal.Order;
import com.gireesh.modal.Product;
import com.gireesh.modal.Refund;
import com.gireesh.modal.ShiftReport;
import com.gireesh.payload.dto.OrderDTO;
import com.gireesh.payload.dto.ProductDTO;
import com.gireesh.payload.dto.RefundDTO;
import com.gireesh.payload.dto.ShiftReportDTO;

import java.util.List;

public class ShiftReportMapper {

    public static ShiftReportDTO toDTo(ShiftReport shiftReport){
        return ShiftReportDTO.builder()
                .id(shiftReport.getId())
                .shiftEndTime(shiftReport.getShiftEndTime())
                .shiftStartTime(shiftReport.getShiftStartTime())
                .totalSales(shiftReport.getTotalSaleAmount())
                .totalOrders(shiftReport.getTotalOrders())
                .netSale(shiftReport.getNetSale())
                .totalOrders(shiftReport.getTotalOrders())
                .cashierId(shiftReport.getCashier() != null ? shiftReport.getCashier().getId() : null)
                .branchId(shiftReport.getBranch() != null ? shiftReport.getBranch().getId() : null)
                .recentOrders(mapOrders(shiftReport.getRecentOrders()))
                .topSellingProducts(mapProducts(shiftReport.getTopSellingProducts()))
                .refunds(mapRefunds(shiftReport.getRefunds()))
                .totalRefunds(shiftReport.getTotalRefundAmount())
                .paymentSummaries(shiftReport.getPaymentSummaries())
                .build();

    }

    private static List<RefundDTO> mapRefunds(List<Refund> refunds) {
        if(refunds == null) return List.of();
        return refunds.stream()
                .map(RefundMapper::toDto)
                .toList();
    }

    private static List<ProductDTO> mapProducts(List<Product> topSellingProducts) {
        if(topSellingProducts == null) return List.of();
        return topSellingProducts.stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    private static List<OrderDTO> mapOrders(List<Order> orders){
        if(orders == null) return List.of();
        return orders.stream()
                .map(OrderMapper::toDTO)
                .toList();
    }
}
