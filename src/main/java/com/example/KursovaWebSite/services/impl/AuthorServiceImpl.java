package com.example.KursovaWebSite.services.impl;

import com.example.KursovaWebSite.models.book.Author;
import com.example.KursovaWebSite.repositories.AuthorRepository;
import com.example.KursovaWebSite.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }
}
