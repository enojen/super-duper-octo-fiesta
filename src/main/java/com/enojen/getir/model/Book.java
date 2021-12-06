package com.enojen.getir.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EnableMongoAuditing
@Document(collection = "books")
public class Book {

    @Id
    private String id;

    @Version
    private int version;

    @NotEmpty()
    private String title;

    private String description;

    @NotEmpty()
    private String author;

    @DecimalMin(value = "0.0")
    private BigDecimal price;

    @Min(0)
    private Long quantity;

}
