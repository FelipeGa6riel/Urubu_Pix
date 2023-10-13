package com.urubu.pix.services;

import com.urubu.pix.domain.transaction.Transaction;
import com.urubu.pix.domain.transaction.TypeTransaction;
import com.urubu.pix.domain.user.User;
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
    private TransactionRepository transactionRepository;

    public Transaction createTransfer(DataTransaction dataTransaction) {
        var sender = this.userService.findUserById(dataTransaction.senderId());
        var receiver = this.userService.findUserById(dataTransaction.receiverId());

        userService.validateTransaction(sender,dataTransaction.value());

        Transaction transaction = new Transaction();
        transaction.setAmount(dataTransaction.value());
        transaction.setSender(sender);
        transaction.setTypeTransaction(dataTransaction.typeTransaction());
        transaction.setReceiver(receiver);
        transaction.setTimeStamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(dataTransaction.value()));
        receiver.setBalance(receiver.getBalance().add(dataTransaction.value()));

        transactionRepository.save(transaction);
        userService.saveUser(sender);
        userService.saveUser(receiver);
        return transaction;
    }

    public User createDeposit(DataDeposit dataDeposit) {
        var sender = userService.findUserById(dataDeposit.senderId());

        var transaction = new Transaction();

        transaction.setAmount(dataDeposit.value());
        transaction.setSender(sender);
        if(dataDeposit.typeTransaction().equals(TypeTransaction.DEPOSIT)) {
            transaction.setTypeTransaction(dataDeposit.typeTransaction());
        }
        transaction.setReceiver(sender);
        transaction.setTimeStamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().add(dataDeposit.value()));
        transactionRepository.save(transaction);
        userService.saveUser(sender);

        return sender;
    }

    public User createWithDraw(DataDeposit dataDeposit) {
        var withDraw = userService.findUserById(dataDeposit.senderId());

        userService.validateTransaction(withDraw,dataDeposit.value());
        Transaction transaction = new Transaction();
        transaction.setAmount(dataDeposit.value());
        transaction.setTypeTransaction(TypeTransaction.WITHDRAW);
        transaction.setSender(withDraw);
        transaction.setReceiver(withDraw);
        transaction.setTimeStamp(LocalDateTime.now());

        withDraw.setBalance(withDraw.getBalance().subtract(dataDeposit.value()));

        userService.saveUser(withDraw);
        transactionRepository.save(transaction);

        return withDraw;
    }
}
