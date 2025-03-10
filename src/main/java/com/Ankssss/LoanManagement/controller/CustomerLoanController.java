package com.Ankssss.LoanManagement.controller;

import com.Ankssss.LoanManagement.DTO.LoanDTO;
import com.Ankssss.LoanManagement.config.CustomUserDetails;
import com.Ankssss.LoanManagement.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer/loans")
public class CustomerLoanController {

    @Autowired
    private LoanService loanService;

    @GetMapping
    public ResponseEntity<List<LoanDTO>> getAllLoans(
            @RequestParam(required = false) String status,
            Authentication auth
    ){
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        List<LoanDTO> loans = loanService.getAllLoansofUser(userDetails.getUserId(), status);
        return ResponseEntity.ok(loans);
    }

}
