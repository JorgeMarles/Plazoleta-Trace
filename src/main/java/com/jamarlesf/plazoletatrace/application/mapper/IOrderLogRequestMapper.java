package com.jamarlesf.plazoletatrace.application.mapper;

import com.jamarlesf.plazoletatrace.application.dto.request.CancelledLogRequest;
import com.jamarlesf.plazoletatrace.application.dto.request.DeliveredLogRequest;
import com.jamarlesf.plazoletatrace.application.dto.request.PendingLogRequest;
import com.jamarlesf.plazoletatrace.application.dto.request.PreparationLogRequest;
import com.jamarlesf.plazoletatrace.application.dto.request.ReadyLogRequest;
import com.jamarlesf.plazoletatrace.domain.model.OrderLog;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderLogRequestMapper {
    OrderLog toOrderLogFromPending(PendingLogRequest request);
    OrderLog toOrderLogFromPreparation(PreparationLogRequest request);
    OrderLog toOrderLogFromReady(ReadyLogRequest request);
    OrderLog toOrderLogFromDelivered(DeliveredLogRequest request);
    OrderLog toOrderLogFromCancelled(CancelledLogRequest request);
}
