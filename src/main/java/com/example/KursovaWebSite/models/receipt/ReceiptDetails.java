package com.example.KursovaWebSite.models.receipt;

import com.example.KursovaWebSite.models.book.Book;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Receipt_Detail")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "receipt_id", referencedColumnName = "id")
    private Receipt receipt;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    private int amount;

    private double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ReceiptDetails that = (ReceiptDetails) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
