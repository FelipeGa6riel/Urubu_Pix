package com.urubu.pix.services;

import com.urubu.pix.domain.user.User;
import com.urubu.pix.dtos.DataUser;
import com.urubu.pix.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void  validateTransaction(User user, BigDecimal amount) {

        if(user.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }
    }

    public User findUserById(Long id) throws RuntimeException{
      return this.repository.findUserById(id);
//              orElseThrow(() -> new RuntimeException("Usuario não existe ou não encontrado"));
    };
    public User createUser(DataUser dataUser){
        var user = new User(dataUser);
        this.saveUser(user);
        return user;
    }

    public User findByCpf(String cpf) {
       return repository.findUserByCpf(cpf).orElseThrow(()-> new RuntimeException("Usuario não tem permissão"));
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }


    public List<User> finAllUsers() {
        return this.repository.findAll();
    }
    public void saveUser(User user) {
        repository.save(user);
    }

}
