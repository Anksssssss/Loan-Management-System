package com.Ankssss.LoanManagement.controller;

import com.Ankssss.LoanManagement.DTO.EmiPaymentResponseDTO;
import com.Ankssss.LoanManagement.entity.Repayment;
import com.Ankssss.LoanManagement.service.RepaymentService;
import com.Ankssss.LoanManagement.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class RepaymentsController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private RepaymentService repaymentService;

    @PostMapping("/transactions/pay-emi")
    public ResponseEntity<EmiPaymentResponseDTO> payEmi(@RequestParam String loanId){
        EmiPaymentResponseDTO response = transactionService.processEmiPayment(loanId);
        return ResponseEntity.ok(response);
    }

    //api to fetch all the repayment schedule based on status
    @GetMapping("/loan/{loanId}")
    public ResponseEntity<List<Repayment>> getRepayments(
            @PathVariable String loanId,
            @RequestParam(required = false) String status
    ){
        List<Repayment> response = repaymentService.findByLoanIdAndStatus(loanId, status);
        return ResponseEntity.ok(response);
    }

    //api to fetch the next emi just after the last one
    @GetMapping("/next-repayment/{loanId}")
    public ResponseEntity<Object> getNextRepayment(
            @PathVariable String loanId
    ){
        Repayment nextRepayment = repaymentService.getNextRepayment(loanId);
        if(nextRepayment != null){
            return ResponseEntity.ok(nextRepayment);
        }else{
            return ResponseEntity.ok("No Repayments Scheduled");
        }
    }

}
