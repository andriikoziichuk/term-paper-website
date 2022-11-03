package com.example.KursovaWebSite.domain.entity;


import com.example.KursovaWebSite.domain.book.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "buckets")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bucket {
    private static final String SEQ_NAME = "bucket_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany
    @JoinTable(name = "buckets_books",
            joinColumns = @JoinColumn(name = "bucket_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> books;


}
