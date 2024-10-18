package com.eventmanagement.authentication.service.interfaces;

import com.eventmanagement.authentication.models.UserRegistrationDTO;
import com.eventmanagement.authentication.models.Users;
import com.eventmanagement.common.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ApiResponse<?> registerUser(UserRegistrationDTO registrationDTO);
}
