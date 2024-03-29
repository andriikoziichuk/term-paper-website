package com.example.KursovaWebSite.controllers;

import com.example.KursovaWebSite.dtos.UserDTO;
import com.example.KursovaWebSite.services.UserService;
import com.example.KursovaWebSite.utils.UserDTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserDTOValidator userDTOValidator;
    private final UserService userService;

    @Autowired
    public AuthController(UserDTOValidator userDTOValidator, UserService userService) {
        this.userDTOValidator = userDTOValidator;
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerPage(@ModelAttribute("user") UserDTO user) {
        return "/auth/register";
    }

    @PostMapping("/register")
    public String makeRegister(@ModelAttribute("user") @Valid UserDTO user,
                               BindingResult bindingResult) {

        userDTOValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return "/auth/register";

        userService.save(user);

        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public String login() {
        return "/auth/login";
    }

}
