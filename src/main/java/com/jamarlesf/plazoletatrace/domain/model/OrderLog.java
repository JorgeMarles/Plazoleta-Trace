package com.jamarlesf.plazoletatrace.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderLog {
    private String id;
    private Long orderId;
    private Long customerId;
    private String customerEmail;
    private LocalDateTime dateTime;
    private OrderStatus previousStatus;
    private OrderStatus newStatus;
    private Long employeeId;
    private String employeeEmail;
}
