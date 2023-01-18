package com.example.KursovaWebSite.service.impl;

import com.example.KursovaWebSite.models.user.Role;
import com.example.KursovaWebSite.models.user.User;
import com.example.KursovaWebSite.repositories.UserRepository;
import com.example.KursovaWebSite.dto.UserDTO;
import com.example.KursovaWebSite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public boolean save(UserDTO userDTO) {

        User user = User.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .active(true)
                .email(userDTO.getEmail())
                .role("ROLE_USER")
                .build();

        userRepository.save(user);
        return true;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
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
    @Transactional
    public void updateProfile(UserDTO dto) {
        Optional<User> savedUser = userRepository.findByUsername(dto.getUsername());

        if (savedUser.isPresent())
            throw new RuntimeException("User not found by name: " + dto.getUsername());

        boolean isChanged = false;
        if (dto.getPassword() != null
                && !dto.getPassword().isEmpty()) {
            savedUser.get().setPassword(passwordEncoder.encode(dto.getPassword()));
            isChanged = true;
        }

        if (!Objects.equals(dto.getEmail(), savedUser.get().getEmail())) {
            savedUser.get().setEmail(dto.getEmail());
            isChanged = true;
        }

        if (isChanged)
            userRepository.save(savedUser.get());
    }

    @Override
    public void saveUser(User user, String username, Map<String, String> form) {

        user.setUsername(username);
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        for(String key: form.values()) {

            if (roles.contains(key))
                user.setRole(String.valueOf(key));
        }

        userRepository.save(user);
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
                .build();
    }
}
