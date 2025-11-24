package com.clicker.core.domain.user.models.dto;

import com.clicker.core.domain.user.validation.email.UniqueEmail;
import com.clicker.core.domain.user.validation.username.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {

    @NotBlank(message = "Email shouldn't be empty!")
    @Size(min = 11, max = 50)
    @Email(message = "Email should be valid!")
    @UniqueEmail
    private String email;

    @NotBlank(message = "Password shouldn't be empty!")
    @Size(min = 10, max = 40, message = "Password length must be greater than 10 and less than 40!")
    private String password;

    @NotBlank(message = "Username cannot be null or blank!")
    @Size(max = 50, message = "Username length must be than 50!")
    @UniqueUsername
    private String username;
}
