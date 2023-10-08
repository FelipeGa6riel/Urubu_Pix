package com.urubu.pix.dtos;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DataTransaction(
        @NotNull
        BigDecimal value,
        @NotNull
        long senderId,
        @NotNull
        long receiverId) {
}
