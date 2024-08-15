package com.example.credits_app.controller;

import com.example.credits_app.service.AuthService;
import com.example.credits_app.utils.DTO.entityDTO.LoginDTO;
import com.example.credits_app.utils.DTO.entityDTO.RegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginRequest) {
        return ResponseEntity.status(201).body(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

}
