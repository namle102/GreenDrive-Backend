package com.greendrive.backend.dto;

import com.greendrive.backend.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String email;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;
}
