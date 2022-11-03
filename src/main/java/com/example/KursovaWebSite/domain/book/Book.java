package com.example.KursovaWebSite.domain.book;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "books")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private static final String SEQ_NAME = "book_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    @NotBlank(message = "Please, fill the book title")
    @Length(max = 256, message = "Title too long(256)")
    private String title;

    @NotBlank(message = "Please, fill the book description")
    @Length(max = 2048, message = "Title too long(2048)")
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "books_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Author author;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "books_genres",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "books_categories",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @NotBlank(message = "Price cannot be empty")
    private BigDecimal price;

    private String photoPath;
}
