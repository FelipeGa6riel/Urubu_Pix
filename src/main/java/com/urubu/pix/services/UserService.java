package com.urubu.pix.services;

import com.urubu.pix.domain.user.User;
import com.urubu.pix.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void  validateTransaction(User user, BigDecimal amount) {

        if(user.getBalance().compareTo(amount) <= 0) {
            throw new RuntimeException("Saldo insuficiente");
        }
    }

    public User finduserById(Long id) throws RuntimeException{
      return this.repository.findUserById(id).orElseThrow(() -> new RuntimeException("Usuario não existe ou não encontrado"));
    };

    public void saveUser(User user) {
        repository.save(user);
    }
}
