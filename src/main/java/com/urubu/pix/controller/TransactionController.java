package com.urubu.pix.controller;

import com.urubu.pix.domain.transaction.Transaction;
import com.urubu.pix.dtos.DataDeposit;
import com.urubu.pix.dtos.DataTransaction;
import com.urubu.pix.repositories.TransactionRepository;
import com.urubu.pix.services.TransactionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    TransactionRepository transactionRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<Transaction> createTransaction(@RequestBody DataTransaction dataTransaction) {
        var transaction = transactionService.createTransaction(dataTransaction);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Transaction> cashDeposit(@RequestBody DataDeposit dataDeposit) {
        var deposit = transactionService.cashDeposit(dataDeposit);

        return new ResponseEntity<>(deposit,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Transaction>> listTransactionId(@PathVariable Long id) {
        var transaction = transactionRepository.findById(id);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<DataTransaction>> listTransactioByUserId(@RequestBody DataTransaction dataTransaction,@PageableDefault(page=0, size=10,sort={"data"}) Pageable pageable) {
        var dataTransactionListPage = transactionRepository.findTransactioByUserId(dataTransaction.senderId(), pageable);
        return new ResponseEntity<>(dataTransactionListPage,HttpStatus.OK);
    }
}
