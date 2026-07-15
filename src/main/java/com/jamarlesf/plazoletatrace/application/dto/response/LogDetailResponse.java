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
public class LogDetailResponse {
    private String id;
    private String oldStatus;
    private String newStatus;
    private LocalDateTime date;
}
