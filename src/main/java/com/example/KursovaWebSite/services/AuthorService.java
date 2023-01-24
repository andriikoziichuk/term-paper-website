package com.example.KursovaWebSite.services;

import com.example.KursovaWebSite.models.book.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findAll();
}
