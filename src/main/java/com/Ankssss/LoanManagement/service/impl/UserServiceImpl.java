package com.Ankssss.LoanManagement.service.impl;

import com.Ankssss.LoanManagement.DTO.*;
import com.Ankssss.LoanManagement.entity.Role;
import com.Ankssss.LoanManagement.entity.User;
import com.Ankssss.LoanManagement.repository.LoanRepository;
import com.Ankssss.LoanManagement.repository.RoleRepository;
import com.Ankssss.LoanManagement.repository.UserRepository;
import com.Ankssss.LoanManagement.service.LoanApplicationService;
import com.Ankssss.LoanManagement.service.LoanService;
import com.Ankssss.LoanManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private LoanService loanService;

    @Autowired
    private LoanApplicationService loanApplicationService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void saveUser(RegisterRequestDTO dto) {
        if(userRepository.findByEmail(dto.getEmail())!=null){
            throw new IllegalArgumentException("Email already in use!");
        }
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        Role role = roleRepository.findByName("ROLE_"+dto.getType().toUpperCase())
                .orElseThrow(()-> new IllegalArgumentException("Invalid role type!"));
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public UserDetailsDTO fetchUserDetails(String userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        List<LoanDTO> loans = loanService.getAllLoansofUser(userId, null);
        List<AllLoanApplicationsDTO> applications = loanApplicationService.getLoanApplicationsByUserId(userId, null);
        return new UserDetailsDTO(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                applications,
                loans
        );
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findByRoleName("ROLE_CUSTOMER");
        return users.stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getUserName(),
                        user.getEmail()
                        )
                )
                .collect(Collectors.toList());
    }

}
