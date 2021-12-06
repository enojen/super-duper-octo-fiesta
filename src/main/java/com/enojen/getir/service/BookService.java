package com.enojen.getir.service;

import com.enojen.getir.exception.BookAlreadyExists;
import com.enojen.getir.exception.BookNotFound;
import com.enojen.getir.exception.QuantityLessThanZeroException;
import com.enojen.getir.model.Book;
import com.enojen.getir.payload.request.BookRequest;
import com.enojen.getir.payload.response.BaseResponse;
import com.enojen.getir.payload.response.MessageResponse;
import com.enojen.getir.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BaseResponse<Void> createBook(BookRequest request) {


        if (bookRepository.findBookByTitle(request.getTitle()) != null) {
            throw new BookAlreadyExists();
        } else {
            bookRepository.save(
                    Book.builder()
                            .title(request.getTitle())
                            .description(request.getDescription())
                            .author(request.getAuthor())
                            .price(request.getPrice())
                            .quantity(request.getQuantity())
                            .build());

            return new BaseResponse<>(new MessageResponse("Book successfuly added"));
        }


    }

    public BaseResponse<Map<String, Object>> getAllBooks(int page, int size) {

        List<Book> books;

        Pageable paging = PageRequest.of(page, size);

        Page<Book> pageBooks = bookRepository.findAll(paging);

        books = pageBooks.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("books", books);
        response.put("currentPage", pageBooks.getNumber());
        response.put("totalItems", pageBooks.getTotalElements());
        response.put("totalPages", pageBooks.getTotalPages());

        return new BaseResponse<>(response);

    }

    public BaseResponse<Book> updateBookQuantity(String id, long quantity) {
        if (quantity < 0) {
            throw new QuantityLessThanZeroException();
        }

        Optional<Book> bookData = bookRepository.findById(id);
        if (bookData.isPresent()) {
            Book _book = bookData.get();
            _book.setQuantity(quantity);
            return new BaseResponse<>(bookRepository.save(_book));
        } else {
            throw new BookNotFound();
        }
    }

}
