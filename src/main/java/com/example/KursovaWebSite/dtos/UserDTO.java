package com.example.KursovaWebSite.dtos;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 3, max = 20, message = "Username should be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, max = 15, message = "Password should be between 8 and 15 characters")
    private String password;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, max = 15, message = "Password should be between 8 and 15 characters")
    private String matchingPassword;

    @Email(message = "Email is not correct")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    private String role;

    private boolean active;
}
