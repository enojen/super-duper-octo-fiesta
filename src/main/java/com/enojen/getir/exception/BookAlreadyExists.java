package com.enojen.getir.exception;

public class BookAlreadyExists extends BaseException {
    public BookAlreadyExists() {
        super("Book with given title is already exists!");
    }
}
