package com.example.KursovaWebSite.service;

import com.example.KursovaWebSite.dto.BookDTO;
import com.example.KursovaWebSite.dto.BucketDTO;

import java.util.List;

public interface BookService {

    List<BookDTO> getAll();
    void addToUserBucket(Long productId, String username);
}
