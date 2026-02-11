package com.gireesh.mapper;

import com.gireesh.modal.Refund;
import com.gireesh.payload.dto.RefundDTO;

public class RefundMapper {

    public static RefundDTO toDto(Refund refund){
        return RefundDTO.builder()
                .id(refund.getId())
                .orderId(refund.getOrder().getOrderId())
                .reason(refund.getReason())
                .amount(refund.getAmount())
                .cashierId(refund.getCashier().getId())
                .branchId(refund.getBranch().getId())
                .shiftReportId(refund.getShiftReport() != null ? refund.getShiftReport().getId() : null)
                .paymentType(refund.getPaymentType())
                .build();
    }

    public static Refund toEntity(RefundDTO refundDTO){
        return null;
    }
}
