package com.jamarlesf.plazoletatrace.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PreparationLogRequest {
    private Long orderId;
    private Long employeeId;
    private String employeeEmail;
    private LocalDateTime dateTime;
}
