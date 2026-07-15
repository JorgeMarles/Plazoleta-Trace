package com.jamarlesf.plazoletatrace.infrastructure.out.mongodb.mapper;

import com.jamarlesf.plazoletatrace.domain.model.OrderLog;
import com.jamarlesf.plazoletatrace.infrastructure.out.mongodb.entity.OrderLogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderLogEntityMapper {
    OrderLogEntity toEntity(OrderLog orderLog);
    OrderLog toOrderLog(OrderLogEntity orderLogEntity);
}
