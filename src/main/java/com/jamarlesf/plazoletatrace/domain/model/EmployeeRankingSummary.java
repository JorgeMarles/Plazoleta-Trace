package com.jamarlesf.plazoletatrace.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRankingSummary {
    private Long employeeId;
    private String employeeEmail;
    private Double averageDurationSeconds;
}
