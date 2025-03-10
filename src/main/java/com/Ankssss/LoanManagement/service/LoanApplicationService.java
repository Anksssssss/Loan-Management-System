package com.Ankssss.LoanManagement.service;

import com.Ankssss.LoanManagement.DTO.AllLoanApplicationsDTO;
import com.Ankssss.LoanManagement.DTO.LoanApplicationDTO;
import com.Ankssss.LoanManagement.constants.LoanApplicationStatus;
import com.Ankssss.LoanManagement.entity.LoanApplication;

import java.util.List;

public interface LoanApplicationService {

    public String applyForLoan(LoanApplicationDTO dto, String email);

    public List<AllLoanApplicationsDTO> getLoanApplications(String status);

    public List<AllLoanApplicationsDTO> getLoanApplicationsByUserId(String userId, String status);

    public LoanApplication updateStatus(String applicatonId, LoanApplicationStatus status);

}
