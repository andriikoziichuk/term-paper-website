package com.example.KursovaWebSite.domain.book;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "authors")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    private static final String SEQ_NAME = "author_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    @NotBlank(message = "First Name cannot be empty")
    private String firstName;

    @NotBlank(message = "Last Name cannot be empty")
    private String lastName;

    private String fullName = firstName + " " + lastName;

//    @OneToMany
//    private List<Book> books;
}
