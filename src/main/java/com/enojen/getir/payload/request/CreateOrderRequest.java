package com.enojen.getir.payload.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class CreateOrderRequest {
    @NotEmpty(message = "Order list can not be empty")
    private List<OrderBookInput> orderBooks;
}
