package com.Ankssss.LoanManagement.DTO;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterRequestDTO {

    @NotBlank(message = "username cannot be empty")
    private String userName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "email is required")
    private String email;

    @Size(min = 3, message = "Password must be at least 3 characters long")
    private String password;

    @NotBlank(message = "Please specify User Role type")
    private String type;
}
