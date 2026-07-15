package com.jamarlesf.plazoletatrace.infrastructure.out.mongodb.adapter;

import com.jamarlesf.plazoletatrace.domain.model.OrderLog;
import com.jamarlesf.plazoletatrace.domain.spi.IOrderLogPersistencePort;
import com.jamarlesf.plazoletatrace.infrastructure.out.mongodb.mapper.IOrderLogEntityMapper;
import com.jamarlesf.plazoletatrace.infrastructure.out.mongodb.repository.IOrderLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLogMongodbAdapter implements IOrderLogPersistencePort {

    private final IOrderLogRepository orderLogRepository;
    private final IOrderLogEntityMapper orderLogEntityMapper;

    @Override
    public void saveOrderLog(OrderLog orderLog) {
        orderLogRepository.save(orderLogEntityMapper.toEntity(orderLog));
    }

    @Override
    public OrderLog findLastLogByOrderId(Long orderId) {
        return orderLogEntityMapper.toOrderLog(orderLogRepository.findTopByOrderIdOrderByDateTimeDesc(orderId));
    }
}
