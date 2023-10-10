package com.urubu.pix.repositories;

import com.urubu.pix.domain.transaction.Transaction;
import com.urubu.pix.dtos.DataTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT new com.urubu.pix.dtos.DataTransaction(t.sender.id, t.amount,t.receiver.id, t.data) FROM transactions t WHERE t.sender.id = :id")
    Page<DataTransaction> findTransactioByUserId(Long id, Pageable page);
}
