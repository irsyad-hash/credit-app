package com.example.credits_app.service;

import com.example.credits_app.model.Account;
import com.example.credits_app.utils.DTO.entityDTO.AccountDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AccountService {
    Account create(AccountDTO accountDTO, Authentication authentication);
    Account getOne(Integer id, Authentication authentication);
    List<Account> getAll(Authentication authentication);
    Account update(Integer id, Authentication authentication, AccountDTO accountDTO);
    void delete(Integer id, Authentication authentication);
    Account withdraw(Integer id, AccountDTO accountDTO, Authentication authentication);
}
