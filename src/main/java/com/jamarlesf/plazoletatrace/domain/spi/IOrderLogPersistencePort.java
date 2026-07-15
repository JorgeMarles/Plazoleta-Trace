package com.jamarlesf.plazoletatrace.domain.spi;

import com.jamarlesf.plazoletatrace.domain.model.OrderLog;
import com.jamarlesf.plazoletatrace.domain.model.OrderLogSummary;

import java.util.List;

public interface IOrderLogPersistencePort {
    void saveOrderLog(OrderLog orderLog);
    OrderLog findLastLogByOrderId(Long orderId);
    List<OrderLogSummary> findLogsByCustomerId(Long customerId);
}
