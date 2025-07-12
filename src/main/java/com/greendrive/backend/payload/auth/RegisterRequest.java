package com.greendrive.backend.payload.auth;

import com.greendrive.backend.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String firstName;
    private String lastName;

    private Role role; // optional, default USER can be set in service
}
