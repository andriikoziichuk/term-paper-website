package com.example.KursovaWebSite.repositories;

import com.example.KursovaWebSite.models.book.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
