package com.urubu.pix.domain.user;

import com.urubu.pix.dtos.DataUser;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "users")
@Table(name = "users")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String cpf;
    private BigDecimal balance;
    private LocalDate date = LocalDate.now();

    public User(DataUser dataUser) {
        this.id = dataUser.id();
        this.nome = dataUser.nome();
        this.email = dataUser.email();
        this.cpf = dataUser.cpf();
        this.balance = dataUser.balance();
    }

    public void updateUser(DataUser dataUser) {
        this.nome = dataUser.nome();
        this.email = dataUser.email();
    }

    public void updateBalanceUser(User user,BigDecimal balance){
        user.setBalance(user.balance.add(balance));
        System.out.println("dataUser.balance() = " + balance);
    }
}
