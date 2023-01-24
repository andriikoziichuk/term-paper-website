package com.example.KursovaWebSite.services.impl;

import com.example.KursovaWebSite.models.user.User;
import com.example.KursovaWebSite.repositories.UserRepository;
import com.example.KursovaWebSite.dtos.UserDTO;
import com.example.KursovaWebSite.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(UserDTO userDTO) {

        User user = User.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .active(true)
                .email(userDTO.getEmail())
                .role("ROLE_USER")
                .build();

        userRepository.save(user);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void update(User user) {

        Optional<User> find = userRepository.findById(user.getId());

        if (!find.isPresent())
            throw new UsernameNotFoundException("User not found");

        find.get().setRole(user.getRole());
        find.get().setUsername(user.getUsername());
        userRepository.save(find.get());
    }

    @Override
    public UserDTO findByEmailAndMapToDto(String email) {
        Optional<User> user = findByEmail(email);

        if (!user.isPresent())
            throw new RuntimeException("Username not found");

        return toDTO(user.get());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void unblock(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent())
            throw new RuntimeException("User not found");

        user.get().setActive(true);

        userRepository.save(user.get());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void block(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent())
            throw new RuntimeException("User not found");

        user.get().setActive(false);

        userRepository.save(user.get());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserDTO> getAll() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public User findByUsername(String name) {
        return userRepository.findByUsername(name).orElse(null);
    }

    @Override
    public void updateProfile(UserDTO dto) {
        Optional<User> savedUser = userRepository.findByEmail(dto.getEmail());

        if (!savedUser.isPresent()) {
            throw new RuntimeException("User not found by email: " + dto.getEmail());
        }

        savedUser.get().setUsername(dto.getUsername());
        savedUser.get().setEmail(dto.getEmail());
        savedUser.get().setPassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.save(savedUser.get());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private UserDTO toDTO(User user) {

        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .active(user.isActive())
                .build();
    }

    private User toEntity(UserDTO userDTO) {

        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .role(userDTO.getRole())
                .active(userDTO.isActive())
                .build();
    }
}
