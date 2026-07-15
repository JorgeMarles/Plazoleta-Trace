package com.jamarlesf.plazoletatrace.domain.spi;

import com.jamarlesf.plazoletatrace.domain.model.OrderDurationSummary;
import com.jamarlesf.plazoletatrace.domain.model.OrderLog;
import com.jamarlesf.plazoletatrace.domain.model.OrderLogSummary;

import com.jamarlesf.plazoletatrace.domain.model.EmployeeRankingSummary;

import java.time.LocalDate;
import java.util.List;

public interface IOrderLogPersistencePort {
    void saveOrderLog(OrderLog orderLog);
    OrderLog findLastLogByOrderId(Long orderId);
    List<OrderLogSummary> findLogsByCustomerId(Long customerId);
    List<OrderDurationSummary> findAllOrderDurations(LocalDate date);
    OrderLogSummary findSummaryByOrderId(Long orderId);
    List<EmployeeRankingSummary> getEmployeePerformanceRanking();
}
