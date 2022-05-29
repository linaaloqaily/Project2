package com.example.Project2.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

@AllArgsConstructor @Data
public class User {
    @NotEmpty(message = "id is required")
    @Min(value = 3 ,message = "id should be 3 number")
    private String id;

    @NotEmpty(message = "username is required")
    @Min(value = 5 ,message = "username should be 5 character or more than")
    private String username;

    @NotEmpty(message = "password is required")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")
    private String password;

    @Email
    @NotEmpty(message = "email is required")
    private String email;

    @NotEmpty(message = "role is required")
    @Pattern(regexp = "(Admin|Customer)")
    private String role;

    @NotNull(message = "balance is required")
    @Positive(message = "balance should be positive")
    private String balance;
}
