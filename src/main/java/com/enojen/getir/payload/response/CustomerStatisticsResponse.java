package com.enojen.getir.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class CustomerStatisticsResponse {
    List<UserStatisticsResponse> userStatistics;
}