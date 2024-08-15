package com.example.credits_app.repository;

import com.example.credits_app.model.Role;
import com.example.credits_app.model.Status;
import com.example.credits_app.model.Transaction;
import com.example.credits_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query(
            value = "SELECT * FROM TRANSACTIONS WHERE ACCOUNT_ID = ?",
            nativeQuery = true
    )
    List<Transaction> findAllTransaction(Integer account_id);

    @Query(
            value = "SELECT * FROM TRANSACTIONS WHERE ACCOUNT_ID = ?1 AND ID = ?2",
            nativeQuery = true
    )
    Optional<Transaction> findByIdTransaction(Integer account_id, Integer id);

    @Modifying
    @Query(
            value = "INSERT INTO TRANSACTIONS (ACCOUNT_ID, AMOUNT, STATUS, CREATE_AT) VALUES" +
                    "(?1,?2,?3,NOW())",
            nativeQuery = true
    )
    @Transactional
    void saveTransaction(Integer account_id, Long amount, String status);

    @Modifying
    @Query(
            value = "UPDATE TRANSACTIONS SET STATUS = ?1 WHERE ID = ?2",
            nativeQuery = true
    )
    @Transactional
    void updateStatus(String status, Integer id);

    @Modifying
    @Query(
            value = "DELETE FROM TRANSACTIONS WHERE ID = ?1 AND ACCOUNT_ID = ?2",
            nativeQuery = true
    )
    @Transactional
    void deleteTransaction(Integer id, Integer account_id);
}
