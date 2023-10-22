package com.urubu.pix.controller;

import com.urubu.pix.domain.transaction.Transaction;
import com.urubu.pix.domain.user.User;
import com.urubu.pix.dtos.DataDeposit;
import com.urubu.pix.dtos.DataTransaction;
import com.urubu.pix.infra.filter.SecurityFilter;
import com.urubu.pix.repositories.TransactionRepository;
import com.urubu.pix.services.TokenService;
import com.urubu.pix.services.TransactionService;
import com.urubu.pix.services.UserService;
import com.urubu.pix.services.ValidateRequest;
import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Transaction>> listTransactionId(@PathVariable Long id) {
        var transaction = transactionRepository.findById(id);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<DataTransaction>> listTransactioByUserId(@RequestBody DataTransaction dataTransaction, @PageableDefault(page = 0, size = 10, sort = {"timeStamp"}) Pageable pageable) {
        var dataTransactionListPage = transactionRepository.findTransactionsByUserId(dataTransaction.senderId(), pageable);
        return new ResponseEntity<>(dataTransactionListPage, HttpStatus.OK);
    }

    @PostMapping("/transfer")
    @Transactional
    public ResponseEntity<Transaction> createTransaction(@RequestBody DataTransaction dataTransaction, HttpServletRequest request) {
        ValidateRequest.requestValidate(dataTransaction.senderId(),request);
        var transaction = transactionService.createTransfer(dataTransaction);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @PostMapping("/deposit")
    @Transactional
    public ResponseEntity<User> cashDeposit(@RequestBody DataDeposit dataDeposit, HttpServletRequest request) {
        ValidateRequest.requestValidate(dataDeposit.senderId(), request);
        var deposit = transactionService.createDeposit(dataDeposit);
        return new ResponseEntity<>(deposit, HttpStatus.OK);
    }


    @PostMapping("/withdraw")
    @Transactional
    public ResponseEntity<User> createWithDraw(@RequestBody DataDeposit dataDeposit,HttpServletRequest request) {
        ValidateRequest.requestValidate(dataDeposit.senderId(),request);
        var userWithDraw = transactionService.createWithDraw(dataDeposit);

        return new ResponseEntity<User>(userWithDraw, HttpStatus.ACCEPTED);
    }
}
