package com.jamarlesf.plazoletatrace.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
}
