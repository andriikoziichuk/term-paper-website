package com.example.KursovaWebSite.repositories;

import com.example.KursovaWebSite.models.book.Author;
import com.example.KursovaWebSite.models.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select  s from Book s where s.title like ?1%")
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(Author author);
//    Book get(Long id);

    @Query(value = "SELECT * " +
            "FROM book b " +
            "LEFT JOIN  user_book ub ON b.id = ub.book_id " +
            "LEFT JOIN \"user\" u ON u.id = ub.user_id", nativeQuery = true)
    List<Book> getBooks();

    /*
    SELECT i.id, i.title AS item_title, t.title AS tag_title
    FROM   items      i
    JOIN   items_tags it ON it.item_id = i.id
    JOIN   tags       t  ON t.id = it.tag_id;
     */

    @Query("select b from Book b " +
            "where b.description like %?1%" +
            "or b.title like %?1%" +
            "or b.author.lastName like %?1%" +
            "or b.author.firstName like %?1%" +
            "or b.genre.title like %?1%")
    List<Book> searchProduct(String keyword);
}
