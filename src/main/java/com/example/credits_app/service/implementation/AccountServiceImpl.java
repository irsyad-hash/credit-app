package com.example.credits_app.service.implementation;

import com.example.credits_app.model.Account;
import com.example.credits_app.model.User;
import com.example.credits_app.repository.AccountRepository;
import com.example.credits_app.repository.UserRepository;
import com.example.credits_app.service.AccountService;
import com.example.credits_app.utils.DTO.entityDTO.AccountDTO;
import com.example.credits_app.utils.DTO.entityDTO.response.AccountResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Override
    public Account create(AccountDTO accountDTO, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsernameUsers(username).orElseThrow(()-> new RuntimeException("User not found"));
        Account account = Account.builder()
                .user(user)
                .accountNumber(accountDTO.getAccountNumber())
                .balance(0L)
                .build();

        return accountRepository.saveAccount(
                account.getUser().getId(),
                account.getAccountNumber(),
                account.getBalance()
        );
    }

    @Override
    public Account getOne(Integer id, Authentication authentication) {
        User user = userRepository.findByUsernameUsers(authentication.getName()).orElseThrow(()-> new RuntimeException("User not found"));
        return accountRepository.findByUserAndId(user.getId(), id).orElseThrow(()-> new RuntimeException("Account not found"));
    }

    @Override
    public List<Account> getAll(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsernameUsers(username).orElseThrow(()-> new RuntimeException("User not found"));
        return accountRepository.findAllAccount(user.getId());
    }

    @Override
    public Account update(Integer id, Authentication authentication, AccountDTO accountDTO) {
        Account account = getOne(id,authentication);
        accountRepository.findByUserAndId(account.getUser().getId(), id)
                .stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .ifPresent(a -> accountRepository.updateAccount(
                        accountDTO.getAccountNumber(),
                        accountDTO.getBalance(),
                        id,
                        a.getUser().getId()
                ));

        return getOne(id, authentication);
    }

    @Override
    public void delete(Integer id, Authentication authentication) {
        Account account = getOne(id, authentication);
        accountRepository.deleteAccounts(id,account.getUser().getId());
    }

    @Override
    public Account withdraw(Integer id, AccountDTO accountDTO, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsernameUsers(username).orElseThrow(()-> new RuntimeException("User not found"));
        Account account = accountRepository.findByUserAndId(user.getId(), id).orElseThrow(()-> new RuntimeException("Account not found"));
        return accountRepository.updateAccountBalance(account.getBalance() - accountDTO.getBalance(),id, user.getId());
    }
}
