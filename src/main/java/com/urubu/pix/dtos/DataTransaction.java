package com.urubu.pix.dtos;

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
        public DataTransaction(Long senderId, BigDecimal value, LocalDateTime data) {
                this(value, senderId, 0L, data);
        }
}
