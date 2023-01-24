package com.example.KursovaWebSite.models.book;

import com.example.KursovaWebSite.models.receipt.ReceiptDetails;
import com.example.KursovaWebSite.models.user.Bucket;
import com.example.KursovaWebSite.models.user.User;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Book")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Please, fill the book title (Title cannot be empty)")
    @Size(min = 5, max = 256, message = "Title of the book should be between 5 and 256 characters")
    private String title;

    @NotEmpty(message = "Please, fill the book description (Description cannot be empty)")
    @Size(max = 2048, message = "Description of the book should be between 0 and 2048 characters")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private Genre genre;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @Min(value = 0, message = "Price cannot be lower than 0")
    private double price;

    @Lob
    @Column(columnDefinition = "BYTEA")
    private byte[] image;

    @ManyToMany(mappedBy = "books")
    private List<Bucket> buckets;

    @OneToMany(mappedBy = "book")
    private List<ReceiptDetails> receiptDetails;

    @ManyToMany
    @JoinTable(name = "user_book",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"))
    private List<User> owners;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Book book = (Book) o;
        return id != null && Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return title + ", " + owners.toString();
    }
}
