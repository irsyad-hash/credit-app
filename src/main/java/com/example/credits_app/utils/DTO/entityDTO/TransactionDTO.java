package com.example.credits_app.utils.DTO.entityDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TransactionDTO {

    @NotBlank(message = "Balance is required")
    private Long amount;

    @NotBlank(message = "Balance is required")
    private String accountNumber;
}
