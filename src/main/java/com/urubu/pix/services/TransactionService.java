package com.urubu.pix.services;

import com.urubu.pix.domain.transaction.Transaction;
import com.urubu.pix.dtos.DataTransaction;
import com.urubu.pix.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {
    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository repository;

    public Transaction createTransaction(DataTransaction dataTransation) {
        var sender = this.userService.findUserById(dataTransation.senderId());
        var receiver = this.userService.findUserById(dataTransation.receiverId());

        userService.validateTransaction(sender,dataTransation.value());
        Transaction transaction = new Transaction();
        transaction.setAmount(dataTransation.value());
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setData(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(dataTransation.value()));
        receiver.setBalance(receiver.getBalance().add(dataTransation.value()));

        repository.save(transaction);
        userService.saveUser(sender);
        userService.saveUser(receiver);
        return transaction;
    }

}
