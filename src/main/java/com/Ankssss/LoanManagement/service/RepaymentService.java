package com.Ankssss.LoanManagement.service;

import com.Ankssss.LoanManagement.constants.RepaymentStatus;
import com.Ankssss.LoanManagement.entity.Loan;
import com.Ankssss.LoanManagement.entity.Repayment;

import java.util.List;

public interface RepaymentService {

    public void createRepaymentSchedule(Loan loan);

    public Repayment findById(String repaymentId);

    public Repayment updateRepayment(Repayment repayment);

    public List<Repayment> findByLoanIdAndStatus(String loanId, String status);

    public Repayment getNextRepayment(String loanId);

    List<Repayment> getRepaymentsByuserIdAndRole(String userId, String loanId);
}
