package com.jamarlesf.plazoletatrace.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDurationResponse {
    private Long orderId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long totalDurationSeconds;
}
