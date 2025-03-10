package com.Ankssss.LoanManagement.service;

import com.Ankssss.LoanManagement.DTO.AllLoanApplicationsDTO;
import com.Ankssss.LoanManagement.DTO.ApproveApplicationDTO;
import com.Ankssss.LoanManagement.DTO.LoanDTO;
import com.Ankssss.LoanManagement.entity.Loan;
import com.Ankssss.LoanManagement.entity.LoanApplication;

import java.util.List;

public interface LoanService {

    public Loan addLoan(ApproveApplicationDTO dto, LoanApplication applicationsDetails);

    public Loan updateLoan(Loan loan);

    List<LoanDTO> getAllLoansofUser(String userId, String status);

}
