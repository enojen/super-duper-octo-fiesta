package com.enojen.getir.repository;

import com.enojen.getir.model.Statistic;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StatisticRepository extends MongoRepository<Statistic, String> {
    List<Statistic> findAllByUserId(String userId);
}
