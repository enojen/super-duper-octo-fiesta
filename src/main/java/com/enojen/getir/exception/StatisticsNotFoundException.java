package com.enojen.getir.exception;

public class StatisticsNotFoundException extends BaseException {
    public StatisticsNotFoundException() {
        super("Statistics not found for given user");
    }
}
