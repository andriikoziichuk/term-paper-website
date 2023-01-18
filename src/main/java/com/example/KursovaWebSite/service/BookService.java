package com.example.KursovaWebSite.service;

import com.example.KursovaWebSite.dto.BookDTO;

import java.util.List;

public interface BookService {

    List<BookDTO> getAll();
    List<BookDTO> getByName(String title);
    void addToUserBucket(Long productId, String username);
    void addToUserLiked(Long productId, String username);

    void delFromUserBucket(Long productId, String username);
}
