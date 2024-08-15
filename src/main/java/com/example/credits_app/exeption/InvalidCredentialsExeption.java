package com.example.credits_app.exeption;

public class InvalidCredentialsExeption extends RuntimeException {
    public InvalidCredentialsExeption(String message) {
        super(message);
    }
}
