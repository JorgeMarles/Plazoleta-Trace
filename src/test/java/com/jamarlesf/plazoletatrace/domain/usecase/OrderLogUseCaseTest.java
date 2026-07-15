package com.jamarlesf.plazoletatrace.domain.usecase;

import com.jamarlesf.plazoletatrace.domain.exception.DomainException;
import com.jamarlesf.plazoletatrace.domain.model.OrderLog;
import com.jamarlesf.plazoletatrace.domain.model.OrderStatus;
import com.jamarlesf.plazoletatrace.domain.spi.IOrderLogPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderLogUseCaseTest {

    @Mock
    private IOrderLogPersistencePort orderLogPersistencePort;

    @InjectMocks
    private OrderLogUseCase orderLogUseCase;

    private OrderLog requestLog;
    private OrderLog lastLog;

    @BeforeEach
    void setUp() {
        requestLog = new OrderLog();
        requestLog.setOrderId(1L);

        lastLog = new OrderLog();
        lastLog.setOrderId(1L);
        lastLog.setEmployeeId(100L);
        lastLog.setEmployeeEmail("emp@test.com");
    }

    @Test
    void createPendingLog_ShouldSave_WhenNoPreviousLog() {
        when(orderLogPersistencePort.findLastLogByOrderId(1L)).thenReturn(null);
        orderLogUseCase.createPendingLog(requestLog);
        assertEquals(OrderStatus.PENDING, requestLog.getNewStatus());
        assertNull(requestLog.getPreviousStatus());
        verify(orderLogPersistencePort, times(1)).saveOrderLog(requestLog);
    }

    @Test
    void createPendingLog_ShouldThrowException_WhenPreviousLogExists() {
        when(orderLogPersistencePort.findLastLogByOrderId(1L)).thenReturn(lastLog);
        assertThrows(DomainException.class, () -> orderLogUseCase.createPendingLog(requestLog));
    }

    @Test
    void createInPreparationLog_ShouldSave_WhenPreviousIsPending() {
        lastLog.setNewStatus(OrderStatus.PENDING);
        when(orderLogPersistencePort.findLastLogByOrderId(1L)).thenReturn(lastLog);
        
        orderLogUseCase.createInPreparationLog(requestLog);
        
        assertEquals(OrderStatus.IN_PREPARATION, requestLog.getNewStatus());
        assertEquals(OrderStatus.PENDING, requestLog.getPreviousStatus());
        verify(orderLogPersistencePort, times(1)).saveOrderLog(requestLog);
    }

    @Test
    void createReadyLog_ShouldSaveAndInheritEmployee_WhenPreviousIsInPreparation() {
        lastLog.setNewStatus(OrderStatus.IN_PREPARATION);
        when(orderLogPersistencePort.findLastLogByOrderId(1L)).thenReturn(lastLog);
        
        orderLogUseCase.createReadyLog(requestLog);
        
        assertEquals(OrderStatus.READY, requestLog.getNewStatus());
        assertEquals(OrderStatus.IN_PREPARATION, requestLog.getPreviousStatus());
        assertEquals(100L, requestLog.getEmployeeId());
        assertEquals("emp@test.com", requestLog.getEmployeeEmail());
        verify(orderLogPersistencePort, times(1)).saveOrderLog(requestLog);
    }

    @Test
    void createDeliveredLog_ShouldSaveAndInheritEmployee_WhenPreviousIsReady() {
        lastLog.setNewStatus(OrderStatus.READY);
        when(orderLogPersistencePort.findLastLogByOrderId(1L)).thenReturn(lastLog);
        
        orderLogUseCase.createDeliveredLog(requestLog);
        
        assertEquals(OrderStatus.DELIVERED, requestLog.getNewStatus());
        assertEquals(OrderStatus.READY, requestLog.getPreviousStatus());
        assertEquals(100L, requestLog.getEmployeeId());
        verify(orderLogPersistencePort, times(1)).saveOrderLog(requestLog);
    }

    @Test
    void createCancelledLog_ShouldSave_WhenPreviousIsPending() {
        lastLog.setNewStatus(OrderStatus.PENDING);
        when(orderLogPersistencePort.findLastLogByOrderId(1L)).thenReturn(lastLog);
        
        orderLogUseCase.createCancelledLog(requestLog);
        
        assertEquals(OrderStatus.CANCELLED, requestLog.getNewStatus());
        assertEquals(OrderStatus.PENDING, requestLog.getPreviousStatus());
        verify(orderLogPersistencePort, times(1)).saveOrderLog(requestLog);
    }

    @Test
    void invalidTransition_ShouldThrowException() {
        lastLog.setNewStatus(OrderStatus.PENDING);
        when(orderLogPersistencePort.findLastLogByOrderId(1L)).thenReturn(lastLog);
        
        assertThrows(DomainException.class, () -> orderLogUseCase.createReadyLog(requestLog));
    }
}
