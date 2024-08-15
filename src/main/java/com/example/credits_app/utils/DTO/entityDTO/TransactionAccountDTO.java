package com.example.credits_app.utils.DTO.entityDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TransactionAccountDTO {
    @NotBlank(message = "Balance is required")
    private String accountNumber;
}
