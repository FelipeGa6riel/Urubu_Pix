package com.urubu.pix.dtos;

import com.urubu.pix.domain.transaction.Transaction;
import com.urubu.pix.domain.transaction.TypeTransaction;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DataTransaction(
        @NotNull
        BigDecimal value,
        @NotNull
        long senderId,
        @NotNull
        TypeTransaction typeTransaction,
        @NotNull
        long receiverId,
        LocalDateTime data) {

        public DataTransaction {

        }
        public DataTransaction(Long senderId, BigDecimal value,TypeTransaction typeTransaction, Long receiverId,LocalDateTime data) {
                this(value, senderId, typeTransaction,receiverId, data);
        }

        public  DataTransaction(DataTransaction transaction) {
                this(transaction.value,transaction.senderId,transaction.typeTransaction,transaction.receiverId,transaction.data);
        }
}
