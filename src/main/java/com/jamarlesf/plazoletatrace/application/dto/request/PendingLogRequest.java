package com.jamarlesf.plazoletatrace.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PendingLogRequest {
    private Long orderId;
    private Long customerId;
    private String customerEmail;
    private LocalDateTime dateTime;
}
