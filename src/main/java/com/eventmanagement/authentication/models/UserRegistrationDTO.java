package com.eventmanagement.authentication.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$",
            message = "Password must be 8-20 characters long, contain at least one digit, one uppercase letter, one lowercase letter, and one special character"
    )
    private String password;
    @NotBlank
    private String confirmPassword;
    private String role;

}
