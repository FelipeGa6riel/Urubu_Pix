package com.urubu.pix.domain.user;

import com.urubu.pix.dtos.DataUser;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "Users")
@Table(name = "users")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String cpf;
    private BigDecimal balance;
    private LocalDateTime date = LocalDateTime.now();

    public User(DataUser dataUser) {
        this.id = dataUser.id();
        this.name = dataUser.nome();
        this.email = dataUser.email();
        this.cpf = dataUser.cpf();
    }

    public void updateUser(DataUser dataUser) {
        this.name = dataUser.nome();
        this.email = dataUser.email();
    }
}
