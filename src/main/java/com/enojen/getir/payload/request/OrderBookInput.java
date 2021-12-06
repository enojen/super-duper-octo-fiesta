package com.enojen.getir.payload.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class OrderBookInput {

    @NotNull(message = "Id is required")
    private String id;

    @NotNull(message = "Count required")
    @Min(value = 1, message = "Count must greater than zero")
    private int count;
}
