package com.eventmanagement.authentication.service.implementation;

import com.eventmanagement.authentication.models.Users;
import com.eventmanagement.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (user.getRoleId() == 2) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if (user.getRoleId() == 1) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return new User(user.getEmail(), user.getPassword(), authorities);
    }
}
