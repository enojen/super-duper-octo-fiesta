package com.enojen.getir.payload.response;

import com.enojen.getir.model.Book;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderOutput {

    private String id;
    private Long userId;
    private BigDecimal totalPrice;
    private Date dateCreated;
    private List<Book> bookList;
    private Long totalBookCount;
}
