package com.example.credits_app.service;


import com.example.credits_app.utils.DTO.entityDTO.LoginDTO;
import com.example.credits_app.utils.DTO.entityDTO.RegisterDTO;
import com.example.credits_app.utils.DTO.entityDTO.response.LoginResponse;
import com.example.credits_app.utils.DTO.entityDTO.response.RegisterResponse;

public interface AuthService {
    LoginResponse login(LoginDTO loginRequest);
    RegisterResponse register(RegisterDTO registerRequest);

}
