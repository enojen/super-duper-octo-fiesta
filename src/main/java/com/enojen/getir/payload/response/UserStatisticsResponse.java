package com.enojen.getir.payload.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Month;

@Data
public class UserStatisticsResponse {
    private Month month;
    private int totalOrderCount;
    private int totalBookCount;
    private BigDecimal totalPurchasedAmount;
}