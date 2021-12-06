package com.enojen.getir.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EnableMongoAuditing
@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    @Version
    private int version;

    private String userId;

    @DecimalMin(value = "0.0")
    private BigDecimal totalPrice;

    private int bookCount = 0;

    private long orderDate;

    @DBRef
    private List<Book> books;

}
