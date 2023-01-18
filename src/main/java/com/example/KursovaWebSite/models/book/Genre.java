package com.example.KursovaWebSite.models.book;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Genre")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @NotEmpty(message = "Genre cannot be empty")
    @Size(min = 3, max = 20, message = "Title should be between 3 and 20 characters")
    private String title;

    @OneToMany(mappedBy = "genre")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Book> booksGenre;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Genre genre = (Genre) o;
        return id != null && Objects.equals(id, genre.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
