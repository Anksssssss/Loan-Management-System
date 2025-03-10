package com.Ankssss.LoanManagement.service.impl;

import com.Ankssss.LoanManagement.DTO.ApproveApplicationDTO;
import com.Ankssss.LoanManagement.DTO.LoanDTO;
import com.Ankssss.LoanManagement.constants.LoanStatus;
import com.Ankssss.LoanManagement.entity.Loan;
import com.Ankssss.LoanManagement.entity.LoanApplication;
import com.Ankssss.LoanManagement.repository.LoanRepository;
import com.Ankssss.LoanManagement.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Override
    public Loan addLoan(ApproveApplicationDTO request, LoanApplication applicationDetails) {
        Loan loan = new Loan();
        loan.setId(UUID.randomUUID().toString());
        loan.setUser(applicationDetails.getUser());
        loan.setApplication(applicationDetails);
        loan.setAmount(applicationDetails.getRequestedAmount());
        loan.setInterestRate(request.getInterestRate());
        loan.setTenure(request.getTenure());
        loan.setEmiAmount(calculateEmi(
                applicationDetails.getRequestedAmount(),
                request.getInterestRate(),
                request.getTenure()
        ));
        loan.setRemainingAmount(applicationDetails.getRequestedAmount());
        return loanRepository.save(loan);
    }

    @Override
    public Loan updateLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    @Override
    public List<LoanDTO> getAllLoansofUser(String userId, String status) {
        List<Loan> loans;
        if(status != null){
            loans = loanRepository.findByUserIdAndStatus(userId, LoanStatus.valueOf(status.toUpperCase()));
        }else{
            loans = loanRepository.findByUserId(userId);
        }

        if(loans == null){
            throw new IllegalArgumentException("invalid userId or status");
        }

        return loans.stream()
                .map(loan -> new LoanDTO(
                      loan.getId(),
                      loan.getAmount(),
                      loan.getInterestRate(),
                      loan.getTenure(),
                      loan.getEmiAmount(),
                      loan.getPaidAmount(),
                      loan.getRemainingAmount(),
                      loan.getStatus()
                ))
                .collect(Collectors.toList());
    }

    // EMI = (P*r*(1+r)^n)/((1+r)^n-1)
    // r = annual interest rate/12*100

    private BigDecimal calculateEmi(BigDecimal requestedAmount, BigDecimal interestRate, Integer tenure) {
        BigDecimal monthlyInterestRate = interestRate.divide(BigDecimal.valueOf(12 * 100));
        BigDecimal onePlusRPowerN = monthlyInterestRate.add(BigDecimal.ONE).pow(tenure);

        BigDecimal numerator = requestedAmount.multiply(monthlyInterestRate).multiply(onePlusRPowerN);
        BigDecimal denominator = onePlusRPowerN.subtract(BigDecimal.ONE);

        return numerator.divide(denominator, 2, RoundingMode.HALF_UP);
    }

}
