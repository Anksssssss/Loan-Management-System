package com.Ankssss.LoanManagement.repository;

import com.Ankssss.LoanManagement.constants.LoanApplicationStatus;
import com.Ankssss.LoanManagement.entity.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, String> {
    List<LoanApplication> findByStatus(LoanApplicationStatus status);

    List<LoanApplication> findByUserId(String userId);

    List<LoanApplication> findByUserIdAndStatus(String userId, LoanApplicationStatus loanApplicationStatus);
}
