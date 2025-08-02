package com.greendrive.backend.controller;

import com.greendrive.backend.dto.UserDTO;
import com.greendrive.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class UserController {

    private final UserService userService;

    @GetMapping("/admin/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDTO));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(id, userDTO));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
