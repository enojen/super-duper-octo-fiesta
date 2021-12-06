package com.enojen.getir.controller;


import com.enojen.getir.model.Book;
import com.enojen.getir.payload.request.BookRequest;
import com.enojen.getir.payload.response.BaseResponse;
import com.enojen.getir.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@Transactional
@RequiredArgsConstructor
@RequestMapping(value = "/book")
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;

    @PostMapping("create")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create book", description = "Create book", security = {@SecurityRequirement(name = "bearerAuth")})
    public BaseResponse<Void> createBook(@Valid @RequestBody BookRequest input) {

        return bookService.createBook(input);
    }


    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')OR hasRole('CUSTOMER')")
    @Operation(summary = "Get all books", description = "Get all books", security = {@SecurityRequirement(name = "bearerAuth")})
    public BaseResponse<Map<String, Object>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return bookService.getAllBooks(page, size);
    }

    @PutMapping("/book/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update book quantity by id", description = "Update book quantity by id", security = {@SecurityRequirement(name = "bearerAuth")})
    public BaseResponse<Book> updateBook(@PathVariable("id") String id, @RequestParam long quantity) {
        return bookService.updateBookQuantity(id, quantity);
    }
}
