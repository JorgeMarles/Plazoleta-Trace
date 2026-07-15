package com.jamarlesf.plazoletatrace.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderLogSummaryResponse {
    private Long orderId;
    private Long userId;
    private String userEmail;
    private Long employeeId;
    private String employeeEmail;
    private List<LogDetailResponse> logs;
}
