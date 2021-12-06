package com.enojen.getir.exception;

public class UserAlreadyFoundException extends BaseException {

    public UserAlreadyFoundException() {
        super("User already found");
    }
}
