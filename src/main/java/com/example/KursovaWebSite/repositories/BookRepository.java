package com.example.KursovaWebSite.repositories;

import com.example.KursovaWebSite.models.book.Author;
import com.example.KursovaWebSite.models.book.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    @Query("select  s from Book s where s.title like ?1%")
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(Author author);
    Book getReferenceById(Long id);
}
