package com.example.credits_app.utils.DTO.entityDTO.response;

import com.example.credits_app.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {
    private Integer id;
    private String accountNumber;
    private LocalDateTime createAt;
    private Long amount;
    private Status status;
}
