package com.jamarlesf.plazoletatrace.domain.api;

import com.jamarlesf.plazoletatrace.domain.model.OrderDurationSummary;
import com.jamarlesf.plazoletatrace.domain.model.OrderLog;
import com.jamarlesf.plazoletatrace.domain.model.OrderLogSummary;

import com.jamarlesf.plazoletatrace.domain.model.EmployeeRankingSummary;

import java.time.LocalDate;
import java.util.List;

public interface IOrderLogServicePort {
    void createPendingLog(OrderLog orderLog);
    void createInPreparationLog(OrderLog orderLog);
    void createReadyLog(OrderLog orderLog);
    void createDeliveredLog(OrderLog orderLog);
    void createCancelledLog(OrderLog orderLog);
    List<OrderLogSummary> getOrderLogsByCustomerId(Long customerId);
    List<OrderDurationSummary> getAllOrderDurations(LocalDate date);
    OrderLogSummary getOrderLogSummaryByOrderId(Long orderId);
    List<EmployeeRankingSummary> getEmployeePerformanceRanking();
}
