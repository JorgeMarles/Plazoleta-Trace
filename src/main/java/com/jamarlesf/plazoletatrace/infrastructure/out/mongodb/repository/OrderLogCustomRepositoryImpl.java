package com.jamarlesf.plazoletatrace.infrastructure.out.mongodb.repository;

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
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("customerId").is(customerId)),
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
}
