package com.urubu.pix.domain.transaction;

import com.urubu.pix.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name="Transactions")
@Table(name="transactions")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of="id")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TypeTransaction typeTransaction;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    private LocalDateTime timeStamp;

    private int incomeDays;

}
