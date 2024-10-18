package com.eventmanagement.authentication.service.implementation;

import com.eventmanagement.authentication.models.Roles;
import com.eventmanagement.authentication.models.UserRegistrationDTO;
import com.eventmanagement.authentication.models.Users;
import com.eventmanagement.authentication.repository.RoleRepository;
import com.eventmanagement.authentication.repository.UserRepository;
import com.eventmanagement.authentication.service.interfaces.UserService;
import com.eventmanagement.common.ApiResponse;
import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse<?> registerUser(UserRegistrationDTO registrationDTO){

        if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (userRepository.findByEmail(registrationDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already registered");
        }
        Long defaultRoleId = (registrationDTO.getRole() == null || registrationDTO.getRole().isEmpty()) ? 1 : roleRepository.findByName(registrationDTO.getRole())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"))
                .getId();

        Users user = new Users();
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        user.setRoleId(defaultRoleId);
        userRepository.save(user);
        return ApiResponse.success(HttpStatus.CREATED.value(), "User registered successfully.", user.getEmail());
    }

}
