package com.example.KursovaWebSite.dto;

import com.example.KursovaWebSite.models.book.Book;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikedDTO {
    private Double sum;

    private List<Book> likedBooks = new ArrayList<>();

    public void addLikedBook(Book book) {
        likedBooks.add(book);
        sum += book.getPrice();
    }
}
