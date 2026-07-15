package com.jamarlesf.plazoletatrace.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderLogSummary {
    private Long orderId;
    private Long userId; // Mapped from customerId
    private String userEmail; // Mapped from customerEmail
    private Long employeeId;
    private String employeeEmail;
    private List<LogDetail> logs;

    public Long getTotalDurationSeconds() {
        if (logs == null || logs.isEmpty()) {
            return 0L;
        }
        List<LogDetail> sortedLogs = logs.stream()
                .sorted(Comparator.comparing(LogDetail::getDate))
                .toList();

        LogDetail first = sortedLogs.getFirst();
        LogDetail last = sortedLogs.getLast();

        return Duration.between(first.getDate(), last.getDate()).getSeconds();
    }

    public Map<OrderStatus, Long> getDurationPerStateSeconds() {
        Map<OrderStatus, Long> durations = new EnumMap<>(OrderStatus.class);
        if (logs == null || logs.isEmpty()) {
            return durations;
        }

        List<LogDetail> sortedLogs = logs.stream()
                .sorted(Comparator.comparing(LogDetail::getDate))
                .toList();

        for (int i = 0; i < sortedLogs.size() - 1; i++) {
            LogDetail current = sortedLogs.get(i);
            LogDetail next = sortedLogs.get(i + 1);
            long seconds = Duration.between(current.getDate(), next.getDate()).getSeconds();
            durations.put(current.getNewStatus(), seconds);
        }

        return durations;
    }
}
