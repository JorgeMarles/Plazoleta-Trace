package com.jamarlesf.plazoletatrace.infrastructure.input.rest;

import com.jamarlesf.plazoletatrace.application.dto.request.CancelledLogRequest;
import com.jamarlesf.plazoletatrace.application.dto.request.DeliveredLogRequest;
import com.jamarlesf.plazoletatrace.application.dto.request.PendingLogRequest;
import com.jamarlesf.plazoletatrace.application.dto.request.PreparationLogRequest;
import com.jamarlesf.plazoletatrace.application.dto.request.ReadyLogRequest;
import com.jamarlesf.plazoletatrace.application.dto.response.EmployeeRankingSummaryResponse;
import com.jamarlesf.plazoletatrace.application.dto.response.OrderDurationResponse;
import com.jamarlesf.plazoletatrace.application.dto.response.OrderLogSummaryResponse;
import com.jamarlesf.plazoletatrace.application.handler.IOrderLogHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import com.jamarlesf.plazoletatrace.infrastructure.security.utils.SecurityContextUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/v1/logs")
@RequiredArgsConstructor
public class OrderLogRestController {

    private final IOrderLogHandler orderLogHandler;

    @PostMapping("/pending")
    @PreAuthorize("hasAuthority('CLIENTE')")
    public ResponseEntity<Void> logPending(@RequestBody PendingLogRequest request) {
        orderLogHandler.createPendingLog(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/in-preparation")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<Void> logInPreparation(@RequestBody PreparationLogRequest request) {
        orderLogHandler.createInPreparationLog(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/ready")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<Void> logReady(@RequestBody ReadyLogRequest request) {
        orderLogHandler.createReadyLog(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/delivered")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<Void> logDelivered(@RequestBody DeliveredLogRequest request) {
        orderLogHandler.createDeliveredLog(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/cancelled")
    @PreAuthorize("hasAuthority('CLIENTE')")
    public ResponseEntity<Void> logCancelled(@RequestBody CancelledLogRequest request) {
        orderLogHandler.createCancelledLog(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('CLIENTE')")
    public ResponseEntity<List<OrderLogSummaryResponse>> getLogs() {
        Long userId = SecurityContextUtils.getAuthenticatedUserId();
        return ResponseEntity.ok(orderLogHandler.getLogsByUserId(userId));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('PROPIETARIO')")
    public ResponseEntity<List<OrderDurationResponse>> getAllOrderDurations(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            java.time.LocalDate date) {
        return ResponseEntity.ok(orderLogHandler.getAllOrderDurations(date));
    }

    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasAnyAuthority('PROPIETARIO')")
    public ResponseEntity<OrderLogSummaryResponse> getLogByOrderId(@PathVariable Long orderId) {
        OrderLogSummaryResponse response = orderLogHandler.getLogByOrderId(orderId);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ranking")
    @PreAuthorize("hasAuthority('PROPIETARIO')")
    public ResponseEntity<List<EmployeeRankingSummaryResponse>> getEmployeeRanking() {
        return ResponseEntity.ok(orderLogHandler.getEmployeePerformanceRanking());
    }
}
