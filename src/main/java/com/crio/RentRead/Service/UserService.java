package com.crio.RentRead.Service;

import com.crio.RentRead.DTO.UserDTO;
import com.crio.RentRead.Model.User;

public interface UserService 
{
    User registerUser(UserDTO userDTO);
    User getUserByEmail(String email);
    String login(UserDTO userDTO);
}
