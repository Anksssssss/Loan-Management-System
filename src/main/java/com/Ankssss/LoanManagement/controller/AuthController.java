package com.Ankssss.LoanManagement.controller;

import com.Ankssss.LoanManagement.DTO.ApiResponse;
import com.Ankssss.LoanManagement.DTO.LoginRequestDTO;
import com.Ankssss.LoanManagement.DTO.RegisterRequestDTO;
import com.Ankssss.LoanManagement.config.CustomUserDetailService;
import com.Ankssss.LoanManagement.config.JwtGeneratorValidator;
import com.Ankssss.LoanManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtGeneratorValidator jwtGenVal;

    @Autowired
    CustomUserDetailService customUserDetailService;



    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody RegisterRequestDTO dto) {
        userService.saveUser(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "User Registered Successfully"));
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> loginUser(@RequestBody LoginRequestDTO dto) {
//        try {
//            Authentication auth = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
//            );
//            SecurityContextHolder.getContext().setAuthentication(auth);
//            return ResponseEntity
//                    .ok("Login Successful");
//        } catch (BadCredentialsException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password!");
//        }
//    }

    @PostMapping("/login")
    public String generateJwtToken(@RequestBody LoginRequestDTO userDto) throws Exception {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtGenVal.generateToken(authentication);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("message", "Logout Successful");
        return ResponseEntity.ok(responseBody);
    }

}
