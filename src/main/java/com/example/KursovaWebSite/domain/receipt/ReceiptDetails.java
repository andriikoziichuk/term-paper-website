package com.example.KursovaWebSite.domain.receipt;

import com.example.KursovaWebSite.domain.book.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "receipts_details")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDetails {
    private static final String SEQ_NAME = "receipt_details_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "receipt_id")
    private Receipt receipt;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    private BigDecimal amount;
    private BigDecimal price;
}
