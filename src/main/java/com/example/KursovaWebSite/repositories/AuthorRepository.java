package com.example.KursovaWebSite.repositories;

import com.example.KursovaWebSite.models.book.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
//    Author findByFullName(String fullName);
}
