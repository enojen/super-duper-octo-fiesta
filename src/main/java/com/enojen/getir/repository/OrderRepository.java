package com.enojen.getir.repository;

import com.enojen.getir.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    Page<Order> findAll(Pageable pageable);

    Order findByIdAndUserId(String id, String userId);

    List<Order> findAllByOrderDateIsBetween(long startDate, long endDate);

    List<Order> findByUserId(String userId);
}
