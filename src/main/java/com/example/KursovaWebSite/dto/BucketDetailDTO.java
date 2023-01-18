package com.example.KursovaWebSite.dto;

import com.example.KursovaWebSite.models.book.Book;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BucketDetailDTO {

    private String title;
    private Long productId;
    private double price;
    private double amount;
    private Double sum;

    public BucketDetailDTO(Book book) {
        this.title = book.getTitle();
        this.productId = book.getId();
        this.price = book.getPrice();
        this.amount = 1.0;
        this.sum = price;
    }
}
