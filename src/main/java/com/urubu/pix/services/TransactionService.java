package com.urubu.pix.services;

import com.urubu.pix.domain.transaction.Transaction;
import com.urubu.pix.domain.transaction.TypeTransaction;
import com.urubu.pix.domain.user.User;
import com.urubu.pix.dtos.DataDeposit;
import com.urubu.pix.dtos.DataTransaction;
import com.urubu.pix.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class TransactionService {
    
    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction createTransaction(DataTransaction dataTransation) {
        var sender = this.userService.findUserById(dataTransation.senderId());
        var receiver = this.userService.findUserById(dataTransation.receiverId());

        userService.validateTransaction(sender,dataTransation.value());


        Transaction transaction = new Transaction();
        transaction.setAmount(dataTransation.value());
        transaction.setSender(sender);
        transaction.setTypeTransaction(dataTransation.typeTransaction());
        transaction.setReceiver(receiver);
        transaction.setTimeStamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(dataTransation.value()));
        receiver.setBalance(receiver.getBalance().add(dataTransation.value()));

        transactionRepository.save(transaction);
        userService.saveUser(sender);
        userService.saveUser(receiver);
        return transaction;
    }

    public User cashDeposit(DataDeposit dataDeposit) {
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
        transaction.setSender(withDraw);
        transaction.setReceiver(withDraw);
        transaction.setTimeStamp(LocalDateTime.now());

        withDraw.setBalance(withDraw.getBalance().subtract(dataDeposit.value()));

        userService.saveUser(withDraw);
        transactionRepository.save(transaction);

        return withDraw;
    }

//    public BigDecimal getData(Long id) {
//        var dataTransaction = transactionRepository.findTransactioByUserId(id,Pageable.unpaged());
//        var list = new ArrayList<>(dataTransaction.getContent());
//
//        list.sort(Comparator.comparing(DataTransaction::data));
//
//        LocalDateTime dateAfter = list.get(0).data();
//
//
//        BigDecimal valorTotal = BigDecimal.ZERO;
//
//        for(DataTransaction dateTransaction : list){
//
//            LocalDateTime data = dateTransaction.data();
//
//            var diff = ChronoUnit.DAYS.between(dateAfter,data);
//
//            if(dateTransaction.value().compareTo(BigDecimal.ZERO) < 0) {
//
//                valorTotal = valorTotal.add(dateTransaction.value());
//            }else {
//
//                if(valorTotal.compareTo(dateTransaction.value()) > 0) {
//
//                    for (int day = 0; day < diff; day++){
//
//                        valorTotal = valorTotal.multiply(BigDecimal.valueOf(1.0 + 0.3333));
//                    }
//
//                    valorTotal = valorTotal.add(dateTransaction.value());
//                    System.out.println("valor total = " + valorTotal);
//                }else {
//
//                    System.out.println("Saldo insuficiente");
//                }
//            }
//
//
//        }
//
//        var user = userService.findUserById(id);
//
//        return valorTotal;
//    }
}
