package com.enojen.getir.exception;

public class OrderNotFoundInTimeIntervalException extends BaseException {
    public OrderNotFoundInTimeIntervalException() {
        super("Order not found in given time interval");
    }
}
