package com.jamarlesf.plazoletatrace.application.mapper;

import com.jamarlesf.plazoletatrace.application.dto.response.LogDetailResponse;
import com.jamarlesf.plazoletatrace.application.dto.response.OrderLogSummaryResponse;
import com.jamarlesf.plazoletatrace.domain.model.LogDetail;
import com.jamarlesf.plazoletatrace.domain.model.OrderLogSummary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderLogResponseMapper {

    OrderLogSummaryResponse toResponse(OrderLogSummary orderLogSummary);

    List<OrderLogSummaryResponse> toResponseList(List<OrderLogSummary> orderLogSummaryList);

    @Mapping(source = "previousStatus", target = "oldStatus")
    LogDetailResponse toLogDetailResponse(LogDetail logDetail);
}
