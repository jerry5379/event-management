package com.eventmanagement.authentication.service.implementation;

import com.eventmanagement.authentication.models.Users;
import com.eventmanagement.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorizationService {

    @Autowired
    private UserRepository userRepository;

    public boolean hasAdminRole() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            Optional<Users> userOptional = userRepository.findByEmail(username);

            if (userOptional.isPresent()) {
                Users user = userOptional.get();
                return user.getRoleId() == 2; //  2 is the admin role
            }
        }

        return false;
    }
}