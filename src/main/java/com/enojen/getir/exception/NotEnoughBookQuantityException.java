package com.enojen.getir.exception;

public class NotEnoughBookQuantityException extends BaseException {
    public NotEnoughBookQuantityException(String id) {
        super("Not enough book quantity for id: " + id);
    }
}
