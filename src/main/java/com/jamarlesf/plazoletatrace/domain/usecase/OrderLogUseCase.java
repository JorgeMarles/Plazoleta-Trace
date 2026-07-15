package com.jamarlesf.plazoletatrace.domain.usecase;

import com.jamarlesf.plazoletatrace.domain.api.IOrderLogServicePort;
import com.jamarlesf.plazoletatrace.domain.exception.DomainException;
import com.jamarlesf.plazoletatrace.domain.model.OrderLog;
import com.jamarlesf.plazoletatrace.domain.model.OrderLogSummary;
import com.jamarlesf.plazoletatrace.domain.model.OrderStatus;
import com.jamarlesf.plazoletatrace.domain.spi.IOrderLogPersistencePort;

import java.util.List;

public class OrderLogUseCase implements IOrderLogServicePort {

    private final IOrderLogPersistencePort orderLogPersistencePort;

    public OrderLogUseCase(IOrderLogPersistencePort orderLogPersistencePort) {
        this.orderLogPersistencePort = orderLogPersistencePort;
    }

    @Override
    public void createPendingLog(OrderLog orderLog) {
        OrderLog lastLog = orderLogPersistencePort.findLastLogByOrderId(orderLog.getOrderId());
        if (lastLog != null) {
            throw new DomainException("Order already has logs. Cannot create a new PENDING log.");
        }
        orderLog.setPreviousStatus(null);
        orderLog.setNewStatus(OrderStatus.PENDING);
        orderLogPersistencePort.saveOrderLog(orderLog);
    }

    @Override
    public void createInPreparationLog(OrderLog orderLog) {
        OrderLog lastLog = validateAndGetLastLog(orderLog.getOrderId(), OrderStatus.PENDING);
        orderLog.setPreviousStatus(lastLog.getNewStatus());
        orderLog.setNewStatus(OrderStatus.IN_PREPARATION);
        orderLog.setCustomerId(lastLog.getCustomerId());
        orderLog.setCustomerEmail(lastLog.getCustomerEmail());
        orderLogPersistencePort.saveOrderLog(orderLog);
    }

    @Override
    public void createReadyLog(OrderLog orderLog) {
        OrderLog lastLog = validateAndGetLastLog(orderLog.getOrderId(), OrderStatus.IN_PREPARATION);
        orderLog.setPreviousStatus(lastLog.getNewStatus());
        orderLog.setNewStatus(OrderStatus.READY);
        orderLog.setCustomerId(lastLog.getCustomerId());
        orderLog.setCustomerEmail(lastLog.getCustomerEmail());
        orderLog.setEmployeeId(lastLog.getEmployeeId());
        orderLog.setEmployeeEmail(lastLog.getEmployeeEmail());
        orderLogPersistencePort.saveOrderLog(orderLog);
    }

    @Override
    public void createDeliveredLog(OrderLog orderLog) {
        OrderLog lastLog = validateAndGetLastLog(orderLog.getOrderId(), OrderStatus.READY);
        orderLog.setPreviousStatus(lastLog.getNewStatus());
        orderLog.setNewStatus(OrderStatus.DELIVERED);
        orderLog.setCustomerId(lastLog.getCustomerId());
        orderLog.setCustomerEmail(lastLog.getCustomerEmail());
        orderLog.setEmployeeId(lastLog.getEmployeeId());
        orderLog.setEmployeeEmail(lastLog.getEmployeeEmail());
        orderLogPersistencePort.saveOrderLog(orderLog);
    }

    @Override
    public void createCancelledLog(OrderLog orderLog) {
        OrderLog lastLog = validateAndGetLastLog(orderLog.getOrderId(), OrderStatus.PENDING);
        orderLog.setPreviousStatus(lastLog.getNewStatus());
        orderLog.setNewStatus(OrderStatus.CANCELLED);
        orderLog.setCustomerId(lastLog.getCustomerId());
        orderLog.setCustomerEmail(lastLog.getCustomerEmail());
        orderLogPersistencePort.saveOrderLog(orderLog);
    }

    private OrderLog validateAndGetLastLog(Long orderId, OrderStatus expectedPreviousStatus) {
        OrderLog lastLog = orderLogPersistencePort.findLastLogByOrderId(orderId);
        if (lastLog == null) {
            throw new DomainException("Order trace not found for orderId: " + orderId);
        }
        if (lastLog.getNewStatus() != expectedPreviousStatus) {
            throw new DomainException("Invalid state transition. Current state is " + lastLog.getNewStatus() + 
                    ", but expected " + expectedPreviousStatus + " to perform this action.");
        }
        return lastLog;
    }

    @Override
    public List<OrderLogSummary> getOrderLogsByCustomerId(Long customerId) {
        return orderLogPersistencePort.findLogsByCustomerId(customerId);
    }
}
