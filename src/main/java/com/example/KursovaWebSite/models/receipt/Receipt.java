package com.example.KursovaWebSite.models.receipt;

import com.example.KursovaWebSite.models.user.User;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Receipt")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private double sum;

    @NotEmpty(message = "Address cannot be empty")
    @Size(min = 3, max = 200, message = "Address should be between 3 and 200 characters")
    private String address;

    @OneToMany(mappedBy = "receipt")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<ReceiptDetails> details;

    private String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Receipt receipt = (Receipt) o;
        return id != null && Objects.equals(id, receipt.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
