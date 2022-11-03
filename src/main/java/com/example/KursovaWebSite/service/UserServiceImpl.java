package com.example.KursovaWebSite.service;

import com.example.KursovaWebSite.domain.entity.Role;
import com.example.KursovaWebSite.domain.entity.User;
import com.example.KursovaWebSite.dao.UserRepository;
import com.example.KursovaWebSite.dto.UserDTO;
import com.example.KursovaWebSite.mapper.BookMapper;
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

    public UserServiceImpl(UserRepository userRepository,@Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean save(UserDTO userDTO) {
        if (!Objects.equals(userDTO.getPassword(), userDTO.getMatchingPassword())) {
            throw new RuntimeException("Password in not equals");
        }
        User user = User.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .role(Role.USER)
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
        return userRepository.findByUsername(name);
    }

    @Override
    @Transactional
    public void updateProfile(UserDTO dto) {
        User savedUser = userRepository.findByUsername(dto.getUsername());

        if (savedUser == null)
            throw new RuntimeException("User not found by name: " + dto.getUsername());

        boolean isChanged = false;
        if (dto.getPassword() != null
                && !dto.getPassword().isEmpty()) {
            savedUser.setPassword(passwordEncoder.encode(dto.getPassword()));
            isChanged = true;
        }

        if (!Objects.equals(dto.getEmail(), savedUser.getEmail())) {
            savedUser.setEmail(dto.getEmail());
            isChanged = true;
        }

        if (isChanged)
            userRepository.save(savedUser);
    }

    private UserDTO toDTO(User user) {

        return UserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("User nor found with name: " + username);

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                roles
        );
    }
}
