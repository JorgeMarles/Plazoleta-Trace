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
public class OrderDurationSummary {
    private Long orderId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long totalDurationSeconds;
}
