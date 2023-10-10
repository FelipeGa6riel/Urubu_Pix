package com.urubu.pix.dtos;

import java.math.BigDecimal;

public record DataDeposit(
        Long senderId,
        BigDecimal value) {
}
