package com.jamarlesf.plazoletatrace.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CancelledLogRequest {
    private Long orderId;
    private LocalDateTime dateTime;
}
