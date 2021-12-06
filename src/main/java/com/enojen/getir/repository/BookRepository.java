package com.enojen.getir.repository;

import com.enojen.getir.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {
    Page<Book> findAll(Pageable pageable);

    Book findBookByTitle(String title);
}
