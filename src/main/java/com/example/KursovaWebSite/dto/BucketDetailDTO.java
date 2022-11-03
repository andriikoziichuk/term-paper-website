package com.example.KursovaWebSite.dto;

import com.example.KursovaWebSite.domain.book.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BucketDetailDTO {

    private String title;
    private Long productId;
    private BigDecimal price;
    private BigDecimal amount;
    private Double sum;

    public BucketDetailDTO(Book book) {
        this.title = book.getTitle();
        this.productId = book.getId();
        this.price = book.getPrice();
        this.amount = new BigDecimal(1.0);
        this.sum = Double.valueOf(book.getPrice().toString());
    }
}
