package com.urubu.pix.repositories;

import com.urubu.pix.domain.transaction.Transaction;
import com.urubu.pix.dtos.DataTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT new com.urubu.pix.dtos.DataTransaction(t.sender.id, t.amount,t.typeTransaction,t.receiver.id,t.timeStamp) FROM Transactions t WHERE t.sender.id = :id")
    Page<DataTransaction> findTransactionsByUserId(Long id, Pageable page);

    @Query("SELECT t FROM Transactions t WHERE t.sender.id = :id ORDER BY t.timeStamp DESC LIMIT 1")
    Transaction findTransactionByDateRecent(Long id);
    List<Transaction> findTransactionsByTimeStamp(LocalDateTime data);
}
