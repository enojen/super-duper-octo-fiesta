package com.enojen.getir.exception;

public class QuantityLessThanZeroException extends BaseException {
    public QuantityLessThanZeroException() {
        super("Quantity cannot less than zero");
    }
}
