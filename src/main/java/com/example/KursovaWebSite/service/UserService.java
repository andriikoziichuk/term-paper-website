package com.example.KursovaWebSite.service;

import com.example.KursovaWebSite.models.user.User;
import com.example.KursovaWebSite.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    boolean save(UserDTO userDTO);
    void save(User user);

    List<UserDTO> getAll();

    User findByUsername(String name);

    void updateProfile(UserDTO dto);

    void saveUser(User user, String username, Map<String, String> form);

    Optional<User> findByEmail(String email);
}
