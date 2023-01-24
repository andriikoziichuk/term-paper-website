package com.example.KursovaWebSite.controllers;

import com.example.KursovaWebSite.dtos.UserDTO;
import com.example.KursovaWebSite.services.UserService;
import com.example.KursovaWebSite.utils.UserDTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserDTOValidator userDTOValidator;

    @Autowired
    public UserController(UserService userService, UserDTOValidator userDTOValidator) {
        this.userService = userService;
        this.userDTOValidator = userDTOValidator;
    }

    @GetMapping("/profile")
    public String profileUser(Model model, Principal principal) {

        if (principal == null)
            throw new RuntimeException("You are not authorize");

        UserDTO userDTO;
        try {
            userDTO = userService.findByEmailAndMapToDto(principal.getName());
        } catch (UsernameNotFoundException e) {
            return "user/profile";
        }

        model.addAttribute("username", userDTO.getUsername());
        model.addAttribute("user", userDTO);

        return "/user/profile";
    }

    @PostMapping("/profile")
    public String updateProfileUser(@ModelAttribute("user") @Valid UserDTO userDTO,
                                    BindingResult bindingResult,
                                    Principal principal) {

        if (principal == null)
            throw new RuntimeException("You are not authorize");

        userDTOValidator.validate(userDTO, bindingResult);

        if (bindingResult.hasErrors())
            return "/user/profile";

        try {
            userService.updateProfile(userDTO);
        } catch (RuntimeException e) {
            return "/user/profile";
        }

        return "redirect:/user/profile";
    }
}
