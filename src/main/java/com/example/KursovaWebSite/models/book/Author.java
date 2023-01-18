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
@Table(name = "Author")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @NotEmpty(message = "First Name cannot be empty")
    @Size(min = 3, max = 20, message = "First name should be between 3 and 20 characters")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "Last Name cannot be empty")
    @Size(min = 3, max = 20, message = "Last name should be between 3 and 20 characters")
    private String lastName;

    @OneToMany(mappedBy = "author")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Book> myBooks;

    @Transient
//    @Formula("concat(first_name,' ',last_name)")
    private String fullName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Author author = (Author) o;
        return id != null && Objects.equals(id, author.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
