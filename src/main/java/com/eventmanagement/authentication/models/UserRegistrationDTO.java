package com.eventmanagement.authentication.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {
    @NotBlank
    @Email
    @NotNull
    private String email;
    @NotBlank
    @NotNull
    private String password;
    @NotBlank
    private String confirmPassword;
    private String role;

}
