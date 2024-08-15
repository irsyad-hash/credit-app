package com.example.credits_app.utils.DTO.entityDTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {
    private Integer user_id;
    private String accountNumber;
    private Long balance;
}
