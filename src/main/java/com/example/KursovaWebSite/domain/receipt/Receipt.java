package com.example.KursovaWebSite.domain.receipt;

import com.example.KursovaWebSite.domain.entity.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "receipts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {
    private static final String SEQ_NAME = "receipt_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;
    private String description;

    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private BigDecimal sum;
    private String address;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ReceiptDetails> details;
    @Enumerated(EnumType.STRING)
    private ReceiptStatus status;
}
