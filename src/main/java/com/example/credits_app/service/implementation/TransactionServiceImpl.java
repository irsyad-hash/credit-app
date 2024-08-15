package com.example.credits_app.service.implementation;

import com.example.credits_app.model.Account;
import com.example.credits_app.model.Status;
import com.example.credits_app.model.Transaction;
import com.example.credits_app.model.User;
import com.example.credits_app.repository.AccountRepository;
import com.example.credits_app.repository.TransactionRepository;
import com.example.credits_app.repository.UserRepository;
import com.example.credits_app.service.TransactionService;
import com.example.credits_app.utils.DTO.entityDTO.TransactionAccountDTO;
import com.example.credits_app.utils.DTO.entityDTO.TransactionDTO;
import com.example.credits_app.utils.DTO.entityDTO.response.TransactionResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionServiceImpl  implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final Integer limitCreditScore = -2;

    @Override
    public TransactionResponse create(TransactionDTO transactionDTO, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsernameUsers(username).orElseThrow(()-> new RuntimeException("User not found"));
        Account account = accountRepository.findByAccountNumber(user.getId(), transactionDTO.getAccountNumber());
        if(user.getCreditScore().equals(limitCreditScore)) {
            throw new RuntimeException("Can't deposit");
        }
        Transaction transaction = Transaction.builder()
                .account(account)
                .amount(transactionDTO.getAmount())
                .status(Status.PENDING)
                .build();
        transactionRepository.saveTransaction(
                transaction.getAccount().getId(),
                transaction.getAmount(),
                transaction.getStatus().toString()
        );
        System.out.println(account.getBalance());
        System.out.println(transaction.getAmount());
        userRepository.updateUsersCreditScore(user.getCreditScore()-1, user.getId());
        accountRepository.updateAccountBalance(account.getBalance() + transaction.getAmount(), account.getId(), user.getId());
        return TransactionResponse.builder()
                .id(transaction.getId())
                .accountNumber(account.getAccountNumber())
                .amount(transaction.getAmount())
                .status(transaction.getStatus())
                .build();
    }

    @Override
    public TransactionResponse getOne(Integer id, TransactionAccountDTO transactionAccountDTO, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsernameUsers(username).orElseThrow(()-> new RuntimeException("User not found"));
        Account account = accountRepository.findByAccountNumber(user.getId(), transactionAccountDTO.getAccountNumber());
        Transaction transaction = transactionRepository.findByIdTransaction(account.getId(),id).orElseThrow(()-> new RuntimeException("transaction not found"));
        return TransactionResponse.builder()
                .id(transaction.getId())
                .accountNumber(account.getAccountNumber())
                .amount(transaction.getAmount())
                .status(transaction.getStatus())
                .build();
    }

    @Override
    public List<Transaction> getAll(TransactionAccountDTO transactionAccountDTO, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsernameUsers(username).orElseThrow(()-> new RuntimeException("User not found"));
        Account account = accountRepository.findByAccountNumber(user.getId(), transactionAccountDTO.getAccountNumber());
        return transactionRepository.findAllTransaction(account.getId());
    }

    @Override
    public TransactionResponse update(Integer id,TransactionDTO transactionDTO, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsernameUsers(username).orElseThrow(()-> new RuntimeException("User not found"));
        Account account = accountRepository.findByAccountNumber(user.getId(), transactionDTO.getAccountNumber());
        Transaction transaction = transactionRepository.findByIdTransaction(account.getId(),id).orElseThrow(()-> new RuntimeException("transaction not found"));
        if (transactionDTO.getAmount() < transaction.getAmount()){
            throw new RuntimeException("amount is not enough");
        }
        userRepository.updateUsersCreditScore(user.getCreditScore()+1, user.getId());
        accountRepository.updateAccountBalance(transactionDTO.getAmount() - transaction.getAmount(), id, user.getId());
        transactionRepository.updateStatus(String.valueOf(Status.COMPLETED),id);
        List<Account> accounts = accountRepository.findAllAccount(user.getId());
        accounts.stream()
                .filter(acc -> !acc.equals(account))
                .forEach(acc -> accountRepository.updateAccountBalance(acc.getBalance() + transactionDTO.getAmount(), acc.getId(), user.getId()));

        return TransactionResponse.builder()
                .id(transaction.getId())
                .accountNumber(account.getAccountNumber())
                .createAt(transaction.getCreateAt())
                .amount(transaction.getAmount())
                .status(Status.COMPLETED)
                .build();
    }

    @Override
    public void delete(Integer id, TransactionAccountDTO transactionAccountDTO, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsernameUsers(username).orElseThrow(()-> new RuntimeException("User not found"));
        Account account = accountRepository.findByAccountNumber(user.getId(), transactionAccountDTO.getAccountNumber());
        transactionRepository.deleteTransaction(id,account.getId());
    }

}
