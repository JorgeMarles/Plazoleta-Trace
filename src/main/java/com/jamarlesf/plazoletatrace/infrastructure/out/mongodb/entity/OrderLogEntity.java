package com.jamarlesf.plazoletatrace.infrastructure.out.mongodb.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

@Document(collection = "order_logs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderLogEntity {
    @Id
    private String id;
    @Indexed
    private Long orderId;
    private Long customerId;
    private String customerEmail;
    private LocalDateTime dateTime;
    private String previousStatus;
    private String newStatus;
    private Long employeeId;
    private String employeeEmail;
}
