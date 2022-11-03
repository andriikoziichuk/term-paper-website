package com.example.KursovaWebSite.dao;

import com.example.KursovaWebSite.domain.book.Author;
import com.example.KursovaWebSite.domain.book.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findByTitle(String tag);
    List<Book> findByAuthor(Author author);
    Book getReferenceById(Long id);
}
