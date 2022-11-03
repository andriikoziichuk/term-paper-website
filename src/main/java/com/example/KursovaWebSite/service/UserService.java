package com.example.KursovaWebSite.service;

import com.example.KursovaWebSite.domain.entity.User;
import com.example.KursovaWebSite.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    boolean save(UserDTO userDTO);
    void save(User user);

    List<UserDTO> getAll();

    User findByUsername(String name);

    void updateProfile(UserDTO dto);
}
