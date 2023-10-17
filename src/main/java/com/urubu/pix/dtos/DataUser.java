package com.urubu.pix.dtos;

import com.urubu.pix.domain.user.User;

import java.math.BigDecimal;

public record DataUser(
        Long id,
        String nome,
        String email,
        String cpf,
        String password,
        BigDecimal balance) {

    public  DataUser(User user){
        this(user.getId(),user.getName(),user.getEmail(),user.getCpf(),user.getPassword(),user.getBalance());
    }
}
