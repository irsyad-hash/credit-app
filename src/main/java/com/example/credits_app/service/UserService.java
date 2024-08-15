package com.example.credits_app.service;

import com.example.credits_app.model.User;
import com.example.credits_app.utils.DTO.entityDTO.RegisterDTO;

import java.util.List;

public interface UserService {
    User getOne(Integer id);
    List<User> getAll();
    User update(Integer id, RegisterDTO registerDTO);
    void delete(Integer id);
}
