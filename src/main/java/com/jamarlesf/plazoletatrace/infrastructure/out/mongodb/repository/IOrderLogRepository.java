package com.jamarlesf.plazoletatrace.infrastructure.out.mongodb.repository;

import com.jamarlesf.plazoletatrace.infrastructure.out.mongodb.entity.OrderLogEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IOrderLogRepository extends MongoRepository<OrderLogEntity, String> {
    OrderLogEntity findTopByOrderIdOrderByDateTimeDesc(Long orderId);
}
