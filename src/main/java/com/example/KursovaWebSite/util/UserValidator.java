package com.example.KursovaWebSite.util;

import com.example.KursovaWebSite.dto.UserDTO;
import com.example.KursovaWebSite.models.user.User;
import com.example.KursovaWebSite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class UserValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clas) {
        return UserDTO.class.equals(clas);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userDTO = (UserDTO) target;

        Optional<User> byUsername = userService.findByEmail(userDTO.getEmail());

        if (byUsername.isPresent())
            errors.rejectValue("email", "", "User with this email already exist");
        if (!userDTO.getPassword().equals(userDTO.getMatchingPassword()))
            errors.rejectValue("matchingPassword", "", "Passwords should be equals");

    }
}
