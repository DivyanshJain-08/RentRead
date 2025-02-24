package com.crio.RentRead.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crio.RentRead.DTO.UserDTO;
import com.crio.RentRead.Model.Role;
import com.crio.RentRead.Model.User;
import com.crio.RentRead.Service.UserService;

@RestController
@RequestMapping("/users")
public class UserController 
{
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        User registeredUser = userService.registerUser(userDTO);
        return ResponseEntity.ok("User registered successfully with email: " + registeredUser.getEmail());
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email , org.springframework.security.core.Authentication authentication) {
        User loggedInUser = (User) authentication.getPrincipal();
        if (!loggedInUser.getEmail().equals(email) && !loggedInUser.getRole().equals(Role.ADMIN)) 
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    return ResponseEntity.ok(userService.getUserByEmail(email));
    }    

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.login(userDTO));
    }
}
