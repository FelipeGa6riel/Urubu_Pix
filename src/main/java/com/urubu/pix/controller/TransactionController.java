package com.urubu.pix.controller;

import com.urubu.pix.domain.transaction.Transaction;
import com.urubu.pix.dtos.DataTransaction;
import com.urubu.pix.repositories.TransactionRepository;
import com.urubu.pix.services.TransactionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Transaction>> listTransactionId(@PathVariable Long id) {
        var transaction = transactionRepository.findById(id);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<DataTransaction>> listTransactioByUserId(@RequestBody DataTransaction dataTransaction) {
        var transaction = transactionRepository.findTransactioByIdUser(dataTransaction.senderId());
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
}
