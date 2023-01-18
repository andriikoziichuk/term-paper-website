package com.example.KursovaWebSite.models.user;


import com.example.KursovaWebSite.models.book.Book;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Bucket")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bucket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToMany
    @JoinTable(name = "Bucket_Book",
            joinColumns = @JoinColumn(name = "bucket_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> books;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Bucket bucket = (Bucket) o;
        return id != null && Objects.equals(id, bucket.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
