package com.enojen.getir.service;


import com.enojen.getir.exception.BookNotFound;
import com.enojen.getir.exception.NotEnoughBookQuantityException;
import com.enojen.getir.exception.OrderNotFound;
import com.enojen.getir.exception.OrderNotFoundInTimeIntervalException;
import com.enojen.getir.model.Book;
import com.enojen.getir.model.Order;
import com.enojen.getir.model.User;
import com.enojen.getir.payload.request.CreateOrderRequest;
import com.enojen.getir.payload.response.BaseResponse;
import com.enojen.getir.repository.BookRepository;
import com.enojen.getir.repository.OrderRepository;
import com.enojen.getir.repository.UserRepository;
import io.jsonwebtoken.lang.Collections;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final StatisticService statisticsService;


    public BaseResponse<Order> getOrderById(String id, String userId) {
        Order order = orderRepository.findByIdAndUserId(id, userId);
        if (order == null) {
            logger.error("order not found!");
            throw new OrderNotFound();
        }
        logger.info("founded order: " + order);
        return new BaseResponse<>(order);
    }

    public BaseResponse<List<Order>> getOrderByDateInterval(String userId, String startDate, String endDate) throws ParseException {
        //String str = "2017-10-19 16:18:03.779";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date start = df.parse(startDate);
        Date end = df.parse(endDate);
        long epochStart = start.getTime();
        long epochEnd = end.getTime();
        List<Order> orders = orderRepository.findAllByOrderDateIsBetween(epochStart, epochEnd);
        if (!orders.isEmpty()) {
            return new BaseResponse<>(orders);
        } else {
            throw new OrderNotFoundInTimeIntervalException();
        }

    }

    public BaseResponse<Order> createOrder(CreateOrderRequest input, String userId) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<Book> books = new ArrayList<>();
        int bookCount = 0;
        for (int i = 0; i < input.getOrderBooks().size(); i++) {
            Optional<Book> book = bookRepository.findById(input.getOrderBooks().get(i).getId());
            if (book.isPresent()) {
                if (book.get().getQuantity() < input.getOrderBooks().get(i).getCount()) {
                    throw new NotEnoughBookQuantityException(input.getOrderBooks().get(i).getId());
                }
            }
        }

        for (int i = 0; i < input.getOrderBooks().size(); i++) {
            Optional<Book> book = bookRepository.findById(input.getOrderBooks().get(i).getId());
            if (book.isPresent()) {
                if (book.get().getQuantity() < input.getOrderBooks().get(i).getCount()) {
                    throw new NotEnoughBookQuantityException(input.getOrderBooks().get(i).getId());
                } else {
                    bookCount = bookCount + input.getOrderBooks().get(i).getCount();
                    book.get().setQuantity(book.get().getQuantity() - input.getOrderBooks().get(i).getCount());
                    logger.info("updated book quantity: " + book.get().getQuantity());
                    bookRepository.save(book.get());
                    books.add(book.get());
                    totalPrice = totalPrice.add(book.get().getPrice().multiply(new BigDecimal(input.getOrderBooks().get(i).getCount())));
                }
            } else {
                throw new BookNotFound();
            }
        }

        Order order = orderRepository.save(Order.builder()
                .books(books)
                .totalPrice(totalPrice)
                .userId(userId).bookCount(bookCount).orderDate(System.currentTimeMillis())
                .build());

        User user = userRepository.findById(userId).get();

        if (Collections.isEmpty(user.getOrders())) {
            user.setOrders(new ArrayList<>());
        }
        user.getOrders().add(order);
        statisticsService.updateStatisticsForCustomer(userId, order);


        userRepository.save(user);


        return new BaseResponse<>(order);

    }
}