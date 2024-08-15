package com.example.credits_app.service.implementation;

import com.example.credits_app.model.User;
import com.example.credits_app.repository.UserRepository;
import com.example.credits_app.service.UserService;
import com.example.credits_app.utils.DTO.entityDTO.RegisterDTO;
import lombok.AllArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getOne(Integer id) {
        return userRepository.findByIdUsers(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAllUsers();
    }

    @Override
    public User update(Integer id, RegisterDTO registerDTO) {
        userRepository.updateUsers(
                registerDTO.getUsername(),
                passwordEncoder.encode(registerDTO.getPassword()),
                registerDTO.getEmail(),
                id
        );
        return userRepository.findByIdUsers(id);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteUsers(id);
    }
}
