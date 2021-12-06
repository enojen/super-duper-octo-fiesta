package com.enojen.getir.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "statistics")
@EnableMongoAuditing
public class Statistic {
    @Id
    private String id;

    private String userId;

    private String month;

    private Integer totalOrderCount = 0;

    private Integer totalBookCount = 0;

    private BigDecimal totalPurchasedAmount = BigDecimal.ZERO;
}
