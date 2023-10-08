package com.urubu.pix.dtos;

import com.urubu.pix.domain.user.User;

import java.math.BigDecimal;

public record DataUser(
        Long id,
        String nome,
        String email,
        String cpf,
        BigDecimal balance) {

    public  DataUser(User user){
        this(user.getId(),user.getNome(),user.getEmail(),user.getCpf(),user.getBalance());
    }
}
