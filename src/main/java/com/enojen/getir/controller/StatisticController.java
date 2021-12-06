package com.enojen.getir.controller;

import com.enojen.getir.model.Statistic;
import com.enojen.getir.payload.response.BaseResponse;
import com.enojen.getir.service.StatisticService;
import com.enojen.getir.shared.UserUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("statistics")
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    @Operation(summary = "Get Statistic", description = "Get Statistic", security = {@SecurityRequirement(name = "bearerAuth")})
    public BaseResponse<List<Statistic>> getStatistics() {
        String userId = UserUtil.getUserId();
        return statisticService.getStatistics(userId);
    }

}
