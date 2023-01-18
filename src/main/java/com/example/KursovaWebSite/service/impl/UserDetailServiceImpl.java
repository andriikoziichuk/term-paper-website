package com.example.KursovaWebSite.service.impl;

import com.example.KursovaWebSite.models.user.User;
import com.example.KursovaWebSite.repositories.UserRepository;
import com.example.KursovaWebSite.security.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);

        System.out.println(email);
        System.out.println(user.isPresent());

        if (!user.isPresent())
            throw new UsernameNotFoundException("User not found!");

        return new UserDetail(user.get());
    }
}
