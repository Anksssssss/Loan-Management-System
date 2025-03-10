package com.Ankssss.LoanManagement.service;

import com.Ankssss.LoanManagement.DTO.RegisterRequestDTO;
import com.Ankssss.LoanManagement.DTO.UserDTO;
import com.Ankssss.LoanManagement.DTO.UserDetailsDTO;
import com.Ankssss.LoanManagement.entity.User;

import java.util.List;

public interface UserService {

    void saveUser(RegisterRequestDTO dto);

    UserDetailsDTO fetchUserDetails(String userId);

    List<UserDTO> getAllUsers();
}
