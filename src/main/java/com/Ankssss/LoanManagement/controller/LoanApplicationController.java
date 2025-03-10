package com.Ankssss.LoanManagement.controller;

import com.Ankssss.LoanManagement.DTO.AllLoanApplicationsDTO;
import com.Ankssss.LoanManagement.DTO.LoanApplicationDTO;
import com.Ankssss.LoanManagement.DTO.LoanApplicationResponseDTO;
import com.Ankssss.LoanManagement.config.CustomUserDetails;
import com.Ankssss.LoanManagement.service.LoanApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class LoanApplicationController {

    @Autowired
    private LoanApplicationService loanApplicationService;

    //not passing userid here fetching from context so that anyone else cannot apply for loan with someone else's userid
    @PostMapping("/apply")
    public ResponseEntity<LoanApplicationResponseDTO> applyForLoan(@Valid @RequestBody LoanApplicationDTO dto, Principal principal){
        String applicationId = loanApplicationService.applyForLoan(dto, principal.getName());
        LoanApplicationResponseDTO response = new LoanApplicationResponseDTO(
                applicationId,
                "Loan Application submitted successfully!");
        return ResponseEntity.ok(response);
    }

    //api to fetch all loan applications
    @GetMapping("/loan-applications")
    public ResponseEntity<List<AllLoanApplicationsDTO>> fetchUserLoanApplications(
            @RequestParam(value = "status", required = false) String status,
            Authentication auth
    ){
        CustomUserDetails userDetails = (CustomUserDetails)auth.getPrincipal();
        List<AllLoanApplicationsDTO> response = loanApplicationService.getLoanApplicationsByUserId(userDetails.getUserId(), status);
        return ResponseEntity.ok(response);
    }

}
