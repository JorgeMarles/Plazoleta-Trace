package com.jamarlesf.plazoletatrace.application.handler;

import com.jamarlesf.plazoletatrace.application.dto.request.CancelledLogRequest;
import com.jamarlesf.plazoletatrace.application.dto.request.DeliveredLogRequest;
import com.jamarlesf.plazoletatrace.application.dto.request.PendingLogRequest;
import com.jamarlesf.plazoletatrace.application.dto.request.PreparationLogRequest;
import com.jamarlesf.plazoletatrace.application.dto.request.ReadyLogRequest;
import com.jamarlesf.plazoletatrace.application.dto.response.OrderLogSummaryResponse;

import java.util.List;

public interface IOrderLogHandler {
    void createPendingLog(PendingLogRequest request);
    void createInPreparationLog(PreparationLogRequest request);
    void createReadyLog(ReadyLogRequest request);
    void createDeliveredLog(DeliveredLogRequest request);
    void createCancelledLog(CancelledLogRequest request);
    List<OrderLogSummaryResponse> getLogsByUserId(Long userId);
}
