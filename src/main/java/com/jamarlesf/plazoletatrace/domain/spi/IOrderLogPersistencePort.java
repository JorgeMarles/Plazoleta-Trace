package com.jamarlesf.plazoletatrace.domain.spi;

import com.jamarlesf.plazoletatrace.domain.model.OrderLog;

public interface IOrderLogPersistencePort {
    void saveOrderLog(OrderLog orderLog);
    OrderLog findLastLogByOrderId(Long orderId);
}
