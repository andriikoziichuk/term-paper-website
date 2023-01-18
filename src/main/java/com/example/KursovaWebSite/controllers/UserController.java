package com.example.KursovaWebSite.controllers;

import com.example.KursovaWebSite.models.user.Role;
import com.example.KursovaWebSite.models.user.User;
import com.example.KursovaWebSite.dto.UserDTO;
import com.example.KursovaWebSite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.getAll());
        return "user-list";
    }

    @GetMapping("/new")
    public String newUser(Model model) {

        model.addAttribute("user", new UserDTO());
        return "new-user";
    }

    @PostMapping("/new")
    public String addUser(@Valid UserDTO user,
                          BindingResult bindingResult,
                          Model model) {

        String result;
        if ((result = checkInputErrors(user, bindingResult, model, "new-user")) != null)
            return result;

        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String profileUser(Model model, Principal principal) {

        if (principal == null)
            throw new RuntimeException("You are not authorize");

        User user = userService.findByUsername(principal.getName());

        UserDTO dto = UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        model.addAttribute("username", dto.getUsername());
        model.addAttribute("user", dto);

        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfileUser(UserDTO dto,
                                    Principal principal,
                                    BindingResult bindingResult,
                                    Model model) {

        if (principal == null || !Objects.equals(principal.getName(), dto.getUsername()))
            throw new RuntimeException("You are not authorize");

        String result;
        if ((result = checkInputErrors(dto, bindingResult, model, "profile")) != null)
            return result;

        if (dto.getPassword() != null
                && !dto.getPassword().isEmpty()
                && !Objects.equals(dto.getPassword(), dto.getMatchingPassword())) {

            model.addAttribute("user", dto);

            return "profile";
        }

        userService.updateProfile(dto);

        return "redirect:/users/profile";
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "user-id";
    }

    @PostMapping
    public String userSave(@RequestParam("userId") User user,
                           @RequestParam Map<String, String> form,
                           @RequestParam String username,
                           Model model) {
        userService.saveUser(user, username, form);

        return "redirect:/user";
    }

    private String checkInputErrors(UserDTO dto, BindingResult bindingResult, Model model, String template) {
        if (dto.getPassword() != null && !dto.getPassword().equals(dto.getMatchingPassword())) {
            model.addAttribute("password", "Passwords are different!");
            model.addAttribute("user", dto);
            return template;
        }

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.addAttribute("user", dto);
            model.mergeAttributes(errors);
            return template;
        }

        if (!userService.save(dto)) {
            model.addAttribute("user", dto);
            model.addAttribute("usernameError", "User exists!");
            return template;
        }
        return null;
    }
}
