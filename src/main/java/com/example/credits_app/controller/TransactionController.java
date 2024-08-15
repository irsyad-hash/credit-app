package com.example.credits_app.controller;

import com.example.credits_app.service.TransactionService;
import com.example.credits_app.utils.DTO.entityDTO.AccountDTO;
import com.example.credits_app.utils.DTO.entityDTO.TransactionAccountDTO;
import com.example.credits_app.utils.DTO.entityDTO.TransactionDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @Valid
    @PostMapping
    public ResponseEntity<?> create(@RequestBody TransactionDTO transactionDTO, Authentication authentication){
        return ResponseEntity.status(201).body(transactionService.create(transactionDTO,authentication));
    }

    @Valid
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id, @RequestBody TransactionAccountDTO transactionAccountDTO, Authentication authentication){
        return ResponseEntity.ok(transactionService.getOne(id,transactionAccountDTO,authentication));
    }

    @Valid
    @GetMapping
    public ResponseEntity<?> getAll(@RequestBody TransactionAccountDTO transactionAccountDTO, Authentication authentication){
        return ResponseEntity.ok(transactionService.getAll(transactionAccountDTO,authentication));
    }

    @Valid
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody TransactionDTO transactionDTO, Authentication authentication){
        return ResponseEntity.ok(transactionService.update(id,transactionDTO,authentication));
    }

    @Valid
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id,@RequestBody TransactionAccountDTO transactionAccountDTO, Authentication authentication){
        transactionService.delete(id, transactionAccountDTO, authentication);
        return ResponseEntity.noContent().build();
    }

}
