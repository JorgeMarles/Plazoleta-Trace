package com.jamarlesf.plazoletatrace.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRankingSummaryResponse {
    private Long employeeId;
    private String employeeEmail;
    private Double averageDurationSeconds;
}
