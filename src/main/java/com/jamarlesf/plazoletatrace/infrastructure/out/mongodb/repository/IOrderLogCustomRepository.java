package com.jamarlesf.plazoletatrace.infrastructure.out.mongodb.repository;

import com.jamarlesf.plazoletatrace.domain.model.EmployeeRankingSummary;
import com.jamarlesf.plazoletatrace.domain.model.OrderDurationSummary;
import com.jamarlesf.plazoletatrace.domain.model.OrderLogSummary;

import java.time.LocalDate;
import java.util.List;

public interface IOrderLogCustomRepository {
    List<OrderLogSummary> findLogsByCustomerIdGroupedByOrderId(Long customerId);
    List<OrderDurationSummary> findAllOrderDurations(LocalDate date);
    OrderLogSummary findSummaryByOrderId(Long orderId);
    List<EmployeeRankingSummary> getEmployeePerformanceRanking();
}
