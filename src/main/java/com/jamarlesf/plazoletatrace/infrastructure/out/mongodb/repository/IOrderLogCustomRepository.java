package com.jamarlesf.plazoletatrace.infrastructure.out.mongodb.repository;

import com.jamarlesf.plazoletatrace.domain.model.OrderLogSummary;

import java.util.List;

public interface IOrderLogCustomRepository {
    List<OrderLogSummary> findLogsByCustomerIdGroupedByOrderId(Long customerId);
}
