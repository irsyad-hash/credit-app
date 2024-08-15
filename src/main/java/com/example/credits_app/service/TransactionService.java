package com.example.credits_app.service;

import com.example.credits_app.model.Transaction;
import com.example.credits_app.utils.DTO.entityDTO.TransactionAccountDTO;
import com.example.credits_app.utils.DTO.entityDTO.TransactionDTO;
import com.example.credits_app.utils.DTO.entityDTO.response.TransactionResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TransactionService {
    TransactionResponse create(TransactionDTO transactionDTO, Authentication authentication);
    TransactionResponse getOne(Integer id, TransactionAccountDTO transactionAccountDTO, Authentication authentication);
    List<Transaction> getAll(TransactionAccountDTO transactionAccountDTO,Authentication authentication);
    TransactionResponse update(Integer id, TransactionDTO transactionDTO, Authentication authentication);
    void delete(Integer id, TransactionAccountDTO transactionAccountDTO, Authentication authentication);
}
