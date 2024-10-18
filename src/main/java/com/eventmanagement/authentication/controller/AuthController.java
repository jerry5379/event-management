package com.eventmanagement.authentication.controller;

import com.eventmanagement.authentication.models.LoginDTO;
import com.eventmanagement.authentication.models.UserRegistrationDTO;
import com.eventmanagement.authentication.service.implementation.UserDetailsServiceImpl;
import com.eventmanagement.authentication.service.interfaces.UserService;
import com.eventmanagement.common.ApiResponse;
import com.eventmanagement.config.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;


    @Operation(
            tags = "Authentication Events API",
            description = "Register the user with proper email",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Success"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error"
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody @Valid UserRegistrationDTO registrationDTO) {
        ApiResponse<?> registeredUser = userService.registerUser(registrationDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(registeredUser);
    }

    @Operation(
            tags = "Authentication Events API",
            description = "Login valid user name password.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Success"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error"
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
            );
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getEmail());
            final String jwt = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(ApiResponse.success(200, "Login successful", jwt));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(ApiResponse.error(401, "Invalid email or password"));
        }
    }

}
