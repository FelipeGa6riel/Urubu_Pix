package com.urubu.pix.dtos;

import com.urubu.pix.domain.transaction.TypeTransaction;

import java.math.BigDecimal;

public record DataDeposit(
        Long senderId,
        BigDecimal value,
        TypeTransaction typeTransaction) {
}
