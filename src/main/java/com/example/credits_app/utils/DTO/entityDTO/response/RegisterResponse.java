package com.example.credits_app.utils.DTO.entityDTO.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
    private String username;
    private String email;

}
