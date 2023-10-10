package com.urubu.pix.dtos;

import com.urubu.pix.domain.transaction.Transaction;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DataTransaction(
        @NotNull
        BigDecimal value,
        @NotNull
        long senderId,
        @NotNull
        long receiverId,
        LocalDateTime data) {

        public DataTransaction {

        }
        public DataTransaction(Long senderId, BigDecimal value, Long receiverId,LocalDateTime data) {
                this(value, senderId, receiverId, data);
        }

        public  DataTransaction(Transaction transaction) {
                this(transaction.getAmount(),transaction.getSender().getId(),transaction.getReceiver().getId(),transaction.getData());
        }
}
