package com.example.credits_app.utils.DTO.entityDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AccountDTO {
    @NotBlank(message = "Account Number is required")
    private String accountNumber;

    @NotBlank(message = "Balance is required")
    private Long balance;
}
