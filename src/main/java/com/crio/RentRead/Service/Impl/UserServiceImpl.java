package com.crio.RentRead.Service.Impl;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.crio.RentRead.DTO.UserDTO;
import com.crio.RentRead.Model.Role;
import com.crio.RentRead.Model.User;
import com.crio.RentRead.Repository.UserRepository;
import com.crio.RentRead.Service.UserService;

@Service
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder , AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }   

    @Override
    public User registerUser(UserDTO userDTO) 
    {
        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if(existingUser.isPresent())
        {
            throw new IllegalArgumentException("User with email " + userDTO.getEmail() + " already exists.");
        }
        
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setRole(userDTO.getRole() != null ? userDTO.getRole() : Role.USER);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Hash password

        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) 
    {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public String login(UserDTO userDTO) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken
            (userDTO.getEmail(), userDTO.getPassword()));
        return "Login successful. Use Basic Auth (Email:Password) for protected endpoints.";
    }

    
    
}
