package com.example.KursovaWebSite.repository;

import com.example.KursovaWebSite.domain.book.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByFullName(String fullName);
}
