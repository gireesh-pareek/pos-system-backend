package com.gireesh.service.impl;

import com.gireesh.mapper.RefundMapper;
import com.gireesh.modal.Branch;
import com.gireesh.modal.Order;
import com.gireesh.modal.Refund;
import com.gireesh.modal.User;
import com.gireesh.payload.dto.RefundDTO;
import com.gireesh.repository.OrderRepository;
import com.gireesh.repository.RefundRepository;
import com.gireesh.service.RefundService;
import com.gireesh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RefundServiceImpl implements RefundService {

    private final UserService userService;
    private final RefundRepository refundRepository;
    private final OrderRepository orderRepository;

    @Override
    public RefundDTO createRefund(RefundDTO refundDTO) {
        User cashier = userService.getCurrentUser();
        Order order = orderRepository.findById(refundDTO.getOrderId()).orElseThrow(() -> new RuntimeException("Order not found with ID " + refundDTO.getOrderId()));
        Branch branch = order.getBranch();
        Refund refund = Refund.builder()
                .order(order)
                .cashier(cashier)
                .branch(branch)
                .reason(refundDTO.getReason())
                .amount(refundDTO.getAmount() != null ? refundDTO.getAmount() : order.getTotalAmount())
                .paymentType(order.getPaymentType())
                .createdAt(LocalDateTime.now())
                .build();

        Refund savedRefund = refundRepository.save(refund);
        return RefundMapper.toDto(savedRefund);
    }

    @Override
    public List<RefundDTO> getAllRefunds() {
        return refundRepository.findAll().stream()
                .map(RefundMapper::toDto)
                .toList();
    }

    @Override
    public List<RefundDTO> getRefundByCashier(Long cashierId) {
        return refundRepository.findByCashierId(cashierId).stream()
                .map(RefundMapper::toDto)
                .toList();
    }

    @Override
    public List<RefundDTO> getRefundByShiftReport(Long shiftReportId) {
        return refundRepository.findByShiftReportId(shiftReportId).stream()
                .map(RefundMapper::toDto)
                .toList();
    }

    @Override
    public List<RefundDTO> getRefundByCashierAndDateRange(Long cashierId, LocalDateTime startDate, LocalDateTime endDate) {
        return refundRepository.findByCashier_IdAndCreatedAtBetween(cashierId, startDate, endDate).stream()
                .map(RefundMapper::toDto)
                .toList();
    }

    @Override
    public List<RefundDTO> getRefundByBranch(Long branchId) {
        return refundRepository.findByBranchId(branchId).stream()
                .map(RefundMapper::toDto)
                .toList();
    }

    @Override
    public RefundDTO getRefundById(Long refundId) {
        return RefundMapper.toDto(refundRepository.findById(refundId).orElseThrow(() -> new RuntimeException("Refund not found with id " + refundId)));
    }

    @Override
    public void deleteRefund(Long refundId) {
        this.getRefundById(refundId);
        refundRepository.deleteById(refundId);
    }
}
