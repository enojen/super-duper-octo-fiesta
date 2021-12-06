package com.enojen.getir.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {
    @NotEmpty(message = "Book title is required")
    private String title;

    private String description;

    @NotEmpty(message = "Book's author is required")
    private String author;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price can not be less than zero.")
    private BigDecimal price;

    @Min(value = 1, message = "Quantity can not be less than zero.")
    private Long quantity;

}
