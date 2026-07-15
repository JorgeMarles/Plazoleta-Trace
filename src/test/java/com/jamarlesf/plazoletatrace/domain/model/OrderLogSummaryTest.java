package com.jamarlesf.plazoletatrace.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderLogSummaryTest {

    @Test
    void shouldCalculateTotalDurationSeconds() {
        // Arrange
        OrderLogSummary summary = new OrderLogSummary();
        List<LogDetail> logs = new ArrayList<>();
        
        LogDetail log1 = new LogDetail("1", null, OrderStatus.PENDING, LocalDateTime.of(2023, 10, 1, 10, 0));
        LogDetail log2 = new LogDetail("2", OrderStatus.PENDING, OrderStatus.IN_PREPARATION, LocalDateTime.of(2023, 10, 1, 10, 15));
        LogDetail log3 = new LogDetail("3", OrderStatus.IN_PREPARATION, OrderStatus.READY, LocalDateTime.of(2023, 10, 1, 10, 30));
        LogDetail log4 = new LogDetail("4", OrderStatus.READY, OrderStatus.DELIVERED, LocalDateTime.of(2023, 10, 1, 10, 45));
        
        logs.add(log1);
        logs.add(log3); // Add out of order to test sorting
        logs.add(log2);
        logs.add(log4);
        
        summary.setLogs(logs);

        // Act
        Long duration = summary.getTotalDurationSeconds();

        // Assert
        assertEquals(2700L, duration); // 45 minutes * 60 seconds
    }

    @Test
    void shouldCalculateDurationPerStateSeconds() {
        // Arrange
        OrderLogSummary summary = new OrderLogSummary();
        List<LogDetail> logs = new ArrayList<>();
        
        LogDetail log1 = new LogDetail("1", null, OrderStatus.PENDING, LocalDateTime.of(2023, 10, 1, 10, 0));
        LogDetail log2 = new LogDetail("2", OrderStatus.PENDING, OrderStatus.IN_PREPARATION, LocalDateTime.of(2023, 10, 1, 10, 15));
        LogDetail log3 = new LogDetail("3", OrderStatus.IN_PREPARATION, OrderStatus.READY, LocalDateTime.of(2023, 10, 1, 10, 35));
        LogDetail log4 = new LogDetail("4", OrderStatus.READY, OrderStatus.DELIVERED, LocalDateTime.of(2023, 10, 1, 10, 45));
        
        logs.add(log1);
        logs.add(log2);
        logs.add(log3);
        logs.add(log4);
        
        summary.setLogs(logs);

        // Act
        Map<OrderStatus, Long> durations = summary.getDurationPerStateSeconds();

        // Assert
        assertEquals(900L, durations.get(OrderStatus.PENDING)); // From PENDING to IN_PREPARATION: 15 mins
        assertEquals(1200L, durations.get(OrderStatus.IN_PREPARATION)); // From IN_PREPARATION to READY: 20 mins
        assertEquals(600L, durations.get(OrderStatus.READY)); // From READY to DELIVERED: 10 mins
        assertEquals(3, durations.size());
    }

    @Test
    void shouldReturnZeroIfNoLogs() {
        OrderLogSummary summary = new OrderLogSummary();
        summary.setLogs(new ArrayList<>());

        assertEquals(0L, summary.getTotalDurationSeconds());
        assertEquals(0, summary.getDurationPerStateSeconds().size());
    }
}
