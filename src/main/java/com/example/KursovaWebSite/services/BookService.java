package com.example.KursovaWebSite.services;

import com.example.KursovaWebSite.dtos.BookDTO;
import com.example.KursovaWebSite.models.book.Book;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<BookDTO> getAll();

    Optional<Book> findById(Long id);
    void addToUserBucket(Long productId, String username);
    void addToUserLiked(Long productId, String username);

    void delFromUserBucket(Long productId, String username);

    List<BookDTO> getFavouriteBooks(String email);

    void update(Book book, MultipartFile image);

    List<BookDTO> searchProduct(String keyword);
}
