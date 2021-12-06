package com.enojen.getir.controller;

import com.enojen.getir.model.Order;
import com.enojen.getir.payload.request.CreateOrderRequest;
import com.enojen.getir.payload.response.BaseResponse;
import com.enojen.getir.service.OrderService;
import com.enojen.getir.shared.UserUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;


@RestController
@RequestMapping(value = "/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('CUSTOMER')")
    @Operation(summary = "Create Order", description = "Create Order", security = {@SecurityRequirement(name = "bearerAuth")})
    public BaseResponse<Order> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        String userId = UserUtil.getUserId();
        return orderService.createOrder(request, userId);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('CUSTOMER')")
    @Operation(summary = "Get user Order by id", description = "Get user Order by id", security = {@SecurityRequirement(name = "bearerAuth")})
    public BaseResponse<Order> getOrderById(
            @PathVariable("id") String id
    ) {
        String userId = UserUtil.getUserId();
        return orderService.getOrderById(id, userId);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('CUSTOMER')")
    @Operation(summary = "Get orders time interval", description = "Get orders time interval", security = {@SecurityRequirement(name = "bearerAuth")})
    public BaseResponse<List<Order>> getAllBooks(
            @RequestParam() String startDate,
            @RequestParam() String endDate
    ) throws ParseException {
        String userId = UserUtil.getUserId();
        return orderService.getOrderByDateInterval(userId, startDate, endDate);
    }

}