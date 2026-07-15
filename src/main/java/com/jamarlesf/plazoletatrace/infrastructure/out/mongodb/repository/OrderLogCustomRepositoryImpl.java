package com.jamarlesf.plazoletatrace.infrastructure.out.mongodb.repository;

import com.jamarlesf.plazoletatrace.domain.model.EmployeeRankingSummary;
import com.jamarlesf.plazoletatrace.domain.model.OrderDurationSummary;
import com.jamarlesf.plazoletatrace.domain.model.OrderLogSummary;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderLogCustomRepositoryImpl implements IOrderLogCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<OrderLogSummary> findLogsByCustomerIdGroupedByOrderId(Long customerId) {
        return executeOrderLogSummaryAggregation(Criteria.where("customerId").is(customerId));
    }

    @Override
    public List<OrderDurationSummary> findAllOrderDurations(java.time.LocalDate date) {
        Criteria criteria = new Criteria();
        if (date != null) {
            criteria = Criteria.where("dateTime").gte(date.atStartOfDay()).lt(date.plusDays(1).atStartOfDay());
        }

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("orderId")
                        .first("orderId").as("orderId")
                        .min("dateTime").as("startTime")
                        .max("dateTime").as("endTime"),
                Aggregation.project("orderId", "startTime", "endTime")
                        .andExpression("(endTime - startTime) / 1000").as("totalDurationSeconds"),
                Aggregation.sort(Sort.Direction.DESC, "startTime")
        );

        AggregationResults<OrderDurationSummary> results =
                mongoTemplate.aggregate(aggregation, "order_logs", OrderDurationSummary.class);
        return results.getMappedResults();
    }

    @Override
    public OrderLogSummary findSummaryByOrderId(Long orderId) {
        List<OrderLogSummary> results = executeOrderLogSummaryAggregation(Criteria.where("orderId").is(orderId));
        return results.isEmpty() ? null : results.getFirst();
    }

    private List<OrderLogSummary> executeOrderLogSummaryAggregation(Criteria matchCriteria) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(matchCriteria),
                Aggregation.sort(Sort.Direction.DESC, "dateTime"),
                Aggregation.group("orderId")
                        .first("orderId").as("orderId")
                        .first("customerId").as("userId")
                        .first("customerEmail").as("userEmail")
                        .first("employeeId").as("employeeId")
                        .first("employeeEmail").as("employeeEmail")
                        .push(
                                new Document("id", "$_id")
                                        .append("previousStatus", "$previousStatus")
                                        .append("newStatus", "$newStatus")
                                        .append("date", "$dateTime")
                        ).as("logs"),
                Aggregation.sort(Sort.Direction.DESC, "logs.0.date")
        );

        AggregationResults<OrderLogSummary> results = mongoTemplate.aggregate(aggregation, "order_logs", OrderLogSummary.class);
        return results.getMappedResults();
    }

    @Override
    public List<EmployeeRankingSummary> getEmployeePerformanceRanking() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("employeeId").exists(true).ne(null)),
                Aggregation.group("orderId")
                        .first("employeeId").as("employeeId")
                        .first("employeeEmail").as("employeeEmail")
                        .min("dateTime").as("startDateTime")
                        .max("dateTime").as("endDateTime"),
                Aggregation.project("employeeId", "employeeEmail")
                        .andExpression("(endDateTime - startDateTime) / 1000").as("durationSeconds"),
                Aggregation.group("employeeId")
                        .first("employeeId").as("employeeId")
                        .first("employeeEmail").as("employeeEmail")
                        .avg("durationSeconds").as("averageDurationSeconds"),
                Aggregation.sort(Sort.Direction.ASC, "averageDurationSeconds")
        );

        AggregationResults<EmployeeRankingSummary> results =
                mongoTemplate.aggregate(aggregation, "order_logs", EmployeeRankingSummary.class);
        return results.getMappedResults();
    }
}
