package com.greendrive.backend.service;

import com.greendrive.backend.exception.APIException;
import com.greendrive.backend.model.User;
import com.greendrive.backend.dto.UserDTO;
import com.greendrive.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final ModelMapper modelMapper;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new APIException("User not found with id: " + id, HttpStatus.NOT_FOUND));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new APIException("Email already registered", HttpStatus.CONFLICT);
        }

        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new APIException("Username already taken", HttpStatus.CONFLICT);
        }

        User user = modelMapper.map(userDTO, User.class);
        user.setRole(userDTO.getRole() != null ? userDTO.getRole() : com.greendrive.backend.model.enums.Role.USER);

        // Encode the password
        user.setPassword(encoder.encode(userDTO.getPassword()));

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new APIException("User not found with id: " + id, HttpStatus.NOT_FOUND));

        existingUser.setUsername(userDTO.getUsername());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPassword(userDTO.getPassword());
        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setRole(userDTO.getRole());

        User updatedUser = userRepository.save(existingUser);
        return modelMapper.map(updatedUser, UserDTO.class);
    }

    @Override
    public void deleteUser(Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new APIException("User not found with id: " + id, HttpStatus.NOT_FOUND));
        userRepository.delete(existingUser);
    }
}
