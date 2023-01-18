package com.example.KursovaWebSite.models.user;

import com.example.KursovaWebSite.models.receipt.Receipt;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "public")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 3, max = 20, message = "Username should be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "Password cannot be empty")
//    @Size(min = 8, max = 15, message = "Password should be between 8 and 15 characters")
    private String password;

    private boolean active;

    @Email(message = "Email is not correct")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    private String activationCode;

    @OneToMany(mappedBy = "user")
    private List<Receipt> receipts;

//    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
//    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
//    @Enumerated(EnumType.STRING)
    private String role;//Set<Role> roles;

    @OneToOne(mappedBy = "user")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Bucket bucket;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
