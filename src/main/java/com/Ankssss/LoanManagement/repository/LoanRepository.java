package com.Ankssss.LoanManagement.repository;

import com.Ankssss.LoanManagement.constants.LoanStatus;
import com.Ankssss.LoanManagement.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LoanRepository extends JpaRepository<Loan, String> {
    List<Loan> findByUserIdAndStatus(String userId, LoanStatus loanStatus);

    List<Loan> findByUserId(String userId);
}
