package com.example.credits_app.controller;

import com.example.credits_app.service.AccountService;
import com.example.credits_app.utils.DTO.entityDTO.AccountDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @Valid
    @PostMapping
    public ResponseEntity<?> create(@RequestBody AccountDTO accountDTO, Authentication authentication){
        return ResponseEntity.status(201).body(accountService.create(accountDTO,authentication));
    }

    @Valid
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id, Authentication authentication){
        return ResponseEntity.ok(accountService.getOne(id,authentication));
    }

    @Valid
    @GetMapping
    public ResponseEntity<?> getAll(Authentication authentication){
        return ResponseEntity.ok(accountService.getAll(authentication));
    }

    @Valid
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody AccountDTO accountDTO, Authentication authentication){
        return ResponseEntity.ok(accountService.update(id, authentication, accountDTO));
    }

    @Valid
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id, Authentication authentication){
        accountService.delete(id, authentication);
        return ResponseEntity.noContent().build();
    }

    @Valid
    @PutMapping("/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable Integer id, @RequestBody AccountDTO accountDTO, Authentication authentication){
        return ResponseEntity.ok(accountService.withdraw(id,accountDTO,authentication));
    }
}
