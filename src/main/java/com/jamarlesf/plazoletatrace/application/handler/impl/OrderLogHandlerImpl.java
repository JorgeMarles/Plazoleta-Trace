package com.jamarlesf.plazoletatrace.application.handler.impl;

import com.jamarlesf.plazoletatrace.application.dto.request.CancelledLogRequest;
import com.jamarlesf.plazoletatrace.application.dto.request.DeliveredLogRequest;
import com.jamarlesf.plazoletatrace.application.dto.request.PendingLogRequest;
import com.jamarlesf.plazoletatrace.application.dto.request.PreparationLogRequest;
import com.jamarlesf.plazoletatrace.application.dto.request.ReadyLogRequest;
import com.jamarlesf.plazoletatrace.application.handler.IOrderLogHandler;
import com.jamarlesf.plazoletatrace.application.mapper.IOrderLogRequestMapper;
import com.jamarlesf.plazoletatrace.domain.api.IOrderLogServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLogHandlerImpl implements IOrderLogHandler {

    private final IOrderLogServicePort orderLogServicePort;
    private final IOrderLogRequestMapper orderLogRequestMapper;

    @Override
    public void createPendingLog(PendingLogRequest request) {
        orderLogServicePort.createPendingLog(orderLogRequestMapper.toOrderLogFromPending(request));
    }

    @Override
    public void createInPreparationLog(PreparationLogRequest request) {
        orderLogServicePort.createInPreparationLog(orderLogRequestMapper.toOrderLogFromPreparation(request));
    }

    @Override
    public void createReadyLog(ReadyLogRequest request) {
        orderLogServicePort.createReadyLog(orderLogRequestMapper.toOrderLogFromReady(request));
    }

    @Override
    public void createDeliveredLog(DeliveredLogRequest request) {
        orderLogServicePort.createDeliveredLog(orderLogRequestMapper.toOrderLogFromDelivered(request));
    }

    @Override
    public void createCancelledLog(CancelledLogRequest request) {
        orderLogServicePort.createCancelledLog(orderLogRequestMapper.toOrderLogFromCancelled(request));
    }
}
