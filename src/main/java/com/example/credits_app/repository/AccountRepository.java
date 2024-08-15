package com.example.credits_app.repository;

import com.example.credits_app.model.Account;
import com.example.credits_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query(
            value = "INSERT INTO ACCOUNTS (USER_ID,ACCOUNT_NUMBER,BALANCE) VALUES (?1,?2,?3) RETURNING *",
            nativeQuery = true
    )
    @Transactional
    Account saveAccount(Integer user_id, String account_number, Long balance);

    @Query(
            value = "SELECT * FROM ACCOUNTS WHERE USER_ID = ?1 AND ID = ?2",
            nativeQuery = true
    )
    Optional<Account> findByUserAndId(Integer user_id, Integer id);

    @Query(
            value = "SELECT * FROM ACCOUNTS WHERE USER_ID = ?",
            nativeQuery = true
    )
    List<Account> findAllAccount(Integer id);

    @Query(
            value = "UPDATE ACCOUNTS SET ACCOUNT_NUMBER = ?1, BALANCE = ?2 WHERE ID = ?3 AND USER_ID = ?4 RETURNING *",
            nativeQuery = true
    )
    @Transactional
    Account updateAccount(String accountNumber, Long balance, Integer id, Integer user_id);

    @Modifying
    @Query(
            value = "DELETE FROM ACCOUNTS WHERE ID = ?1 AND USER_ID = ?2",
            nativeQuery = true
    )
    @Transactional
    void deleteAccounts(Integer id, Integer user_id);

    @Query(
            value = "UPDATE ACCOUNTS SET BALANCE = ?1 WHERE ID = ?2 AND USER_ID = ?3 RETURNING *",
            nativeQuery = true
    )
    @Transactional
    Account updateAccountBalance(Long balance, Integer id, Integer user_id);

    @Query(
            value = "SELECT * FROM ACCOUNTS WHERE USER_ID = ?1 AND ACCOUNT_NUMBER = ?2",
            nativeQuery = true
    )
    Account findByAccountNumber(Integer id, String accountNumber);

}
