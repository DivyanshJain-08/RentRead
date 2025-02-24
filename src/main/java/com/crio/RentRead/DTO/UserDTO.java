package com.crio.RentRead.DTO;

import lombok.Getter;
import lombok.Setter;


import com.crio.RentRead.Model.Role;

@Getter
@Setter
public class UserDTO 
{
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Role role; // Default: USER    
}
