package com.example.credits_app.service.implementation;

import com.example.credits_app.exeption.InvalidCredentialsExeption;
import com.example.credits_app.exeption.UserAlreadyExistsException;
import com.example.credits_app.model.Role;
import com.example.credits_app.model.User;
import com.example.credits_app.repository.UserRepository;
import com.example.credits_app.secutiry.JwtUtil;
import com.example.credits_app.service.AuthService;
import com.example.credits_app.utils.DTO.entityDTO.LoginDTO;
import com.example.credits_app.utils.DTO.entityDTO.RegisterDTO;
import com.example.credits_app.utils.DTO.entityDTO.response.LoginResponse;
import com.example.credits_app.utils.DTO.entityDTO.response.RegisterResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final int MIN_PASSWORD_LENGTH = 8;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    @Override
    public LoginResponse login(LoginDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(userDetails);
            return new LoginResponse(accessToken);
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsExeption("Invalid username or password");
        }
    }

    @Override
    public RegisterResponse register(RegisterDTO registerRequest) {
        if (userRepository.existsByUsernameUsers(registerRequest.getUsername())) {
            throw new UserAlreadyExistsException("Username is already taken");
        }

        List<String> passwordErrors = validatePassword(registerRequest.getPassword());
        if (!passwordErrors.isEmpty()) {
            throw new IllegalArgumentException("Password does not meet strength requirements: " + String.join(", ", passwordErrors));
        }

        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .role(Role.USER)
                .creditScore(0)
                .build();

        if (userRepository.existsByEmailUsers(user.getEmail()) || userRepository.existsByUsernameUsers(user.getUsername())) {
            throw new RuntimeException("Email or Username already exists");
        } else {
            userRepository.saveUsers(
                    user.getUsername(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getRole().toString(),
                    user.getCreditScore());
        }

        return RegisterResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    private List<String> validatePassword(String password) {
        List<String> errors = new ArrayList<>();

        if (password.length() < MIN_PASSWORD_LENGTH) {
            errors.add("Password must be at least " + MIN_PASSWORD_LENGTH + " characters long");
        }
        if (!password.matches(".*[A-Z].*")) {
            errors.add("Password must contain at least one uppercase letter");
        }
        if (!password.matches(".*[a-z].*")) {
            errors.add("Password must contain at least one lowercase letter");
        }
        if (!password.matches(".*\\d.*")) {
            errors.add("Password must contain at least one digit");
        }
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            errors.add("Password must contain at least one special character");
        }

        return errors;
    }
}
