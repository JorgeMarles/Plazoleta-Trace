package com.jamarlesf.plazoletatrace.domain.api;

import com.jamarlesf.plazoletatrace.domain.model.OrderLog;

public interface IOrderLogServicePort {
    void createPendingLog(OrderLog orderLog);
    void createInPreparationLog(OrderLog orderLog);
    void createReadyLog(OrderLog orderLog);
    void createDeliveredLog(OrderLog orderLog);
    void createCancelledLog(OrderLog orderLog);
}
