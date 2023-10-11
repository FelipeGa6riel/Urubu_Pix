package com.urubu.pix.services;

import com.urubu.pix.domain.transaction.Transaction;
import com.urubu.pix.dtos.DataDeposit;
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

    public Transaction cashDeposit(DataDeposit dataDeposit) {
        var sender = userService.findUserById(dataDeposit.senderId());
        var receiver = userService.findUserById(dataDeposit.senderId());

        var transaction = new Transaction();

        transaction.setAmount(dataDeposit.value());
        transaction.setSender(sender);
        transaction.setReceiver(sender);
        transaction.setData(LocalDateTime.now());

        sender.setBalance(sender.getBalance().add(dataDeposit.value()));
        repository.save(transaction);
        userService.saveUser(sender);

        return transaction;
    }

    public Transaction createWithDraw(DataDeposit dataDeposit) {
        var withDraw = userService.findUserById(dataDeposit.senderId());

        Transaction transaction = new Transaction();
        transaction.setAmount(dataDeposit.valuea());
        transaction.setSender(withDraw);
        transaction.setReceiver(withDraw);

        withDraw.setBalance(withDraw.getBalance().subtract(dataDeposit.value()));

        userService.save(withDraw);
        repository.save(transaction);

        return transaction;
    }
}
