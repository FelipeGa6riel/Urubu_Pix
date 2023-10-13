package com.urubu.pix.services;

import com.urubu.pix.domain.transaction.Transaction;
import com.urubu.pix.domain.transaction.TypeTransaction;
import com.urubu.pix.domain.user.User;
import com.urubu.pix.repositories.TransactionRepository;
import com.urubu.pix.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@EnableScheduling
public class DailyIncomeService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;
@PostConstruct
    public void init() {
        yieldUpdate();
    }

    //    @Scheduled(fixedDelay = 30000)
    @Scheduled(cron = "0 0 0 * * ?")//meia noite eu te Conto
    public void yieldUpdate() {
        List<User> users = userRepository.findAll();
        LocalDateTime currentData = LocalDateTime.now();

        for(User user: users) {
            BigDecimal currentBalance = user.getBalance();
            BigDecimal dailyIncome = currentBalance.multiply(new BigDecimal("0.333"));
            user.setBalance(currentBalance.add(dailyIncome));
            userRepository.save(user);

            var transaction = new Transaction();
            transaction.setSender(user);
            transaction.setReceiver(user);
            transaction.setTypeTransaction(TypeTransaction.YIELD);
            transaction.setAmount(dailyIncome);
            transaction.setTimeStamp(currentData);

            var lastTransaction = transactionRepository.findTransactionByDateRecent(user.getId());
            if(lastTransaction != null) {
                LocalDateTime lastDeposit = lastTransaction.getTimeStamp();
                long dayYield = Duration.between(lastDeposit,currentData).toDays();
                transaction.setIncomeDays(Math.toIntExact(dayYield));
            }else {
                transaction.setIncomeDays(0);
            }
            transactionRepository.save(transaction);
        }
    }
}
