package com.Ankssss.LoanManagement.controller;

import com.Ankssss.LoanManagement.DTO.*;
import com.Ankssss.LoanManagement.constants.LoanApplicationStatus;
import com.Ankssss.LoanManagement.entity.Loan;
import com.Ankssss.LoanManagement.entity.LoanApplication;
import com.Ankssss.LoanManagement.entity.Repayment;
import com.Ankssss.LoanManagement.service.LoanApplicationService;
import com.Ankssss.LoanManagement.service.LoanService;
import com.Ankssss.LoanManagement.service.RepaymentService;
import com.Ankssss.LoanManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminLoanController {

    @Autowired
    private LoanApplicationService loanApplicationService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private RepaymentService repaymentService;

    @Autowired
    private UserService userService;

    @GetMapping("/loan-applications")
    public ResponseEntity<List<AllLoanApplicationsDTO>> getLoanApplications(
            @RequestParam(value = "status", required = false) String status
    ){
        List<AllLoanApplicationsDTO> response = loanApplicationService.getLoanApplications(status);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/loan-applications/reject")
    public ResponseEntity<ApiResponse> rejectLoanApplication(@Valid @RequestBody RejectApplicationDTO dto){
        loanApplicationService.updateStatus(dto.getApplicationId(), LoanApplicationStatus.REJECTED);
        ApiResponse response = new ApiResponse(true, "Application rejected");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/loan-applications/approve")
    public ResponseEntity<String> approveLoanApplication(@RequestBody ApproveApplicationDTO approveDto){
        //update loan application table
        LoanApplication applicationDetails = loanApplicationService.updateStatus(approveDto.getApplicationId(), LoanApplicationStatus.APPROVED);
        //add data to loan table -> for that i need loan application details
        Loan loan = loanService.addLoan(approveDto, applicationDetails);
        //prepopulate repayments table
        repaymentService.createRepaymentSchedule(loan);

        return ResponseEntity.ok("Loan approved and Repayments set");
    }

    //api to fetch customer details
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDetailsDTO> fetchUserDetails(@PathVariable String userId){
        UserDetailsDTO response = userService.fetchUserDetails(userId);
        return ResponseEntity.ok(response);
    }

    //api to fetch all users
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> fetchAllUsers(){
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    //api to fetch the repayments of a particular loan of a particular customer
    @GetMapping("users/loan-repayment")
    public ResponseEntity<List<Repayment>> getRepaymentsOfAParticularLoanOfUser(
            @RequestParam String userId,
            @RequestParam String loanId
    ){
        List<Repayment> repayments = repaymentService.getRepaymentsByuserIdAndRole(userId, loanId);
        return ResponseEntity.ok(repayments);
    }

}
