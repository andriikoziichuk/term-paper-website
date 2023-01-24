package com.example.KursovaWebSite.services;

import com.example.KursovaWebSite.models.user.User;
import com.example.KursovaWebSite.dtos.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void save(UserDTO userDTO);
    void save(User user);

    List<UserDTO> getAll();

    User findByUsername(String name);

    void updateProfile(UserDTO dto);

    Optional<User> findByEmail(String email);

    void update(User user);

    UserDTO findByEmailAndMapToDto(String name);

    void unblock(Long id);

    void block(Long id);

}
