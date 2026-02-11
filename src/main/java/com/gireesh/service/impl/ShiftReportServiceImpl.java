package com.gireesh.service.impl;

import com.gireesh.domain.PaymentType;
import com.gireesh.mapper.ShiftReportMapper;
import com.gireesh.modal.*;
import com.gireesh.payload.dto.ShiftReportDTO;
import com.gireesh.repository.OrderRepository;
import com.gireesh.repository.RefundRepository;
import com.gireesh.repository.ShiftReportRepository;
import com.gireesh.repository.UserRepository;
import com.gireesh.service.ShiftReportService;
import com.gireesh.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShiftReportServiceImpl implements ShiftReportService {

    private final ShiftReportRepository shiftReportRepository;
    private final UserService userService;
    private final RefundRepository refundRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    public ShiftReportDTO startShift() {
        User currentUser = userService.getCurrentUser();
        Branch branch = currentUser.getBranch();

        LocalDateTime shiftStart = LocalDateTime.now();
        LocalDateTime startOfDay = shiftStart.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = shiftStart.withHour(23).withMinute(59).withSecond(59);

        Optional<ShiftReport> existing = shiftReportRepository.findByCashierAndShiftStartTimeBetween(currentUser, startOfDay, endOfDay);
        if (existing.isPresent()) throw  new RuntimeException("Shift already started");

        ShiftReport shiftReport = ShiftReport.builder()
                .cashier(currentUser)
                .shiftStartTime(shiftStart)
                .branch(branch)
                .build();

        return ShiftReportMapper.toDTo(shiftReportRepository.save(shiftReport));
    }

    @Override
    public ShiftReportDTO endShift() {
        User currentUser = userService.getCurrentUser();

        ShiftReport shiftReport = shiftReportRepository.findTopByCashierAndShiftEndTimeIsNullOrderByShiftStartTimeDesc(currentUser)
                .orElseThrow(() -> new EntityNotFoundException("Could not find shift report with user : " + currentUser.getId()));

        LocalDateTime shiftEndTime = LocalDateTime.now();
        shiftReport.setShiftEndTime(shiftEndTime);

        List<Refund> refunds = refundRepository.findByCashier_IdAndCreatedAtBetween(currentUser.getId(), shiftReport.getShiftStartTime(), shiftEndTime);

        double totalRefund = refunds.stream()
                .mapToDouble(refund -> refund.getAmount() != null ? refund.getAmount() : 0.0)
                .sum();

        List<Order> orders = orderRepository.findByCashier_IdAndCreatedAtBetween(currentUser.getId(), shiftReport.getShiftStartTime(), shiftEndTime);

        double totalSale = orders.stream()
                .mapToDouble(order -> order.getTotalAmount() != null ? order.getTotalAmount() : 0.0)
                .sum();
        shiftReport.setTotalRefundAmount(totalRefund);
        shiftReport.setTotalSaleAmount(totalSale);
        shiftReport.setTotalOrders(orders.size());
        shiftReport.setNetSale(totalSale - totalRefund);
        shiftReport.setRecentOrders(getRecentOrders(orders));
        shiftReport.setTopSellingProducts(getTopSellingProducts(orders));
        shiftReport.setPaymentSummaries(getPaymentSummaries(orders, totalSale));
        shiftReport.setRefunds(refunds);

        return ShiftReportMapper.toDTo(shiftReportRepository.save(shiftReport));
    }

    @Override
    public ShiftReportDTO getShiftReportById(Long id) {
        return ShiftReportMapper.toDTo(
                shiftReportRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Could not find shift report with id: " + id))
        );
    }

    @Override
    public List<ShiftReportDTO> getAllShiftReports() {
        return shiftReportRepository.findAll().stream()
                .map(ShiftReportMapper::toDTo)
                .toList();
    }

    @Override
    public List<ShiftReportDTO> getShiftReportsByBranchId(Long branchId) {
        return shiftReportRepository.findByBranch_id(branchId).stream()
                .map(ShiftReportMapper::toDTo)
                .toList();
    }

    @Override
    public List<ShiftReportDTO> getShiftReportByCashierId(Long cashierId) {
        return shiftReportRepository.findByCashier_id(cashierId).stream()
                .map(ShiftReportMapper::toDTo)
                .toList();
    }

    @Override
    public ShiftReportDTO getCurrentShiftProgress() {
        User currentUser = userService.getCurrentUser();
        ShiftReport shiftReport = shiftReportRepository.findTopByCashierAndShiftEndTimeIsNullOrderByShiftStartTimeDesc(currentUser).orElseThrow(() -> new EntityNotFoundException("No active shift found for user with id: " + currentUser.getId()));

        LocalDateTime currentTime = LocalDateTime.now();

        List<Refund> refunds = refundRepository.findByCashier_IdAndCreatedAtBetween(currentUser.getId(), shiftReport.getShiftStartTime(), currentTime);

        double totalRefund = refunds.stream()
                .mapToDouble(refund -> refund.getAmount() != null ? refund.getAmount() : 0.0)
                .sum();

        List<Order> orders = orderRepository.findByCashier_IdAndCreatedAtBetween(currentUser.getId(), shiftReport.getShiftStartTime(), currentTime);

        double totalSale = orders.stream()
                .mapToDouble(order -> order.getTotalAmount() != null ? order.getTotalAmount() : 0.0)
                .sum();

        shiftReport.setTotalRefundAmount(totalRefund);
        shiftReport.setTotalSaleAmount(totalSale);
        shiftReport.setTotalOrders(orders.size());
        shiftReport.setNetSale(totalSale - totalRefund);
        shiftReport.setRecentOrders(getRecentOrders(orders));
        shiftReport.setTopSellingProducts(getTopSellingProducts(orders));
        shiftReport.setPaymentSummaries(getPaymentSummaries(orders, totalSale));
        shiftReport.setRefunds(refunds);

        return ShiftReportMapper.toDTo(shiftReport);
    }

    @Override
    public ShiftReportDTO getShiftReportByCashierAndDate(Long cashierId, LocalDate date) {
        User cashier = userRepository.findById(cashierId).orElseThrow(() -> new EntityNotFoundException("Can not find cashier with id : " + cashierId));
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atStartOfDay().withHour(23).withMinute(59).withSecond(59);
        ShiftReport shiftReport = shiftReportRepository.findByCashierAndShiftStartTimeBetween(cashier, start, end).orElseThrow(() -> new EntityNotFoundException("Could not find shift report for cashier id: " + cashierId + " and date " + date));
        return ShiftReportMapper.toDTo(shiftReport);
    }


    private List<PaymentSummary> getPaymentSummaries(List<Order> orders, double totalSale) {
        Map<PaymentType, List<Order>> grouped = orders.stream()
                .collect(Collectors.groupingBy(order -> order.getPaymentType() != null ? order.getPaymentType() : PaymentType.CASH));

        List<PaymentSummary> summaries = new ArrayList<>();
        for(Map.Entry<PaymentType, List<Order>> entry : grouped.entrySet()){
            double amount = entry.getValue().stream()
                    .mapToDouble(Order::getTotalAmount)
                    .sum();
            int transactions = entry.getValue().size();
            double percentage = (amount/totalSale)*100;

            PaymentSummary paymentSummary = PaymentSummary.builder()
                    .paymentType(entry.getKey())
                    .totalAmount(amount)
                    .transactionCount(transactions)
                    .percentage(percentage)
                    .build();
            summaries.add(paymentSummary);
        }

        return summaries;
    }

    private List<Product> getTopSellingProducts(List<Order> orders) {
        Map<Product, Integer> productSalesMap = new HashMap<>();

        for(Order order : orders){
            for(OrderItem item: order.getItems()){
                Product product = item.getProduct();
                productSalesMap.put(product, productSalesMap.getOrDefault(product, 0) + item.getQuantity());
            }
        }
        return productSalesMap.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(5)
                .map(Map.Entry::getKey)
                .toList();
    }

    private List<Order> getRecentOrders(List<Order> orders) {
        return orders.stream()
                .sorted(Comparator.comparing(Order::getCreatedAt).reversed())
                .limit(5)
                .toList();
    }
}
