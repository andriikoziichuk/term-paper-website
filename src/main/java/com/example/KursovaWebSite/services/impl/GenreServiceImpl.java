package com.example.KursovaWebSite.services.impl;

import com.example.KursovaWebSite.models.book.Genre;
import com.example.KursovaWebSite.repositories.GenreRepository;
import com.example.KursovaWebSite.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }
}
