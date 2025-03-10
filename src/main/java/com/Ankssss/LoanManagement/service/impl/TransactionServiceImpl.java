package com.Ankssss.LoanManagement.service.impl;

import com.Ankssss.LoanManagement.DTO.EmiPaymentResponseDTO;
import com.Ankssss.LoanManagement.constants.LoanStatus;
import com.Ankssss.LoanManagement.constants.RepaymentStatus;
import com.Ankssss.LoanManagement.constants.TransactionStatus;
import com.Ankssss.LoanManagement.entity.Loan;
import com.Ankssss.LoanManagement.entity.Repayment;
import com.Ankssss.LoanManagement.entity.Transaction;
import com.Ankssss.LoanManagement.repository.LoanRepository;
import com.Ankssss.LoanManagement.repository.TransactionRepository;
import com.Ankssss.LoanManagement.service.LoanService;
import com.Ankssss.LoanManagement.service.RepaymentService;
import com.Ankssss.LoanManagement.service.TransactionService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RepaymentService repaymentService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    @Lazy
    private TransactionServiceImpl self;

    //@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    @Transactional
    public EmiPaymentResponseDTO processEmiPayment(String loanId) {

        Repayment repayment = repaymentService.getNextRepayment(loanId);

        if(repayment==null){
            throw new IllegalArgumentException("No repayments exists for loanId: " + loanId);
        }

        if(repayment.getStatus() == RepaymentStatus.PAID){
            throw  new RuntimeException("This EMI is already paid");
        }

        //set transaction amount
        BigDecimal emiAmount = repayment.getAmount();

        Loan loan = repayment.getLoan();
        Hibernate.initialize(loan);

        createTransaction(loan, repayment, emiAmount);

        //update repayment status
        repayment.setStatus(RepaymentStatus.PAID);
        repayment.setPaymentDate(new Date());
        repaymentService.updateRepayment(repayment);

        //update Loan table
        loan.setPaidAmount(loan.getPaidAmount().add(emiAmount));
        loan.setRemainingAmount(loan.getRemainingAmount().subtract(emiAmount));

        if(loan.getRemainingAmount().compareTo(BigDecimal.ZERO)==0){
            loan.setStatus(LoanStatus.CLOSED);
        }

        loanService.updateLoan(loan);

        return new EmiPaymentResponseDTO(
                new EmiPaymentResponseDTO.TransactionDetails(
                        TransactionStatus.SUCCESSFUL.name(),
                        emiAmount
                ),
                new EmiPaymentResponseDTO.RepaymentDetails(
                        repayment.getStatus().name(),
                        repayment.getId(),
                        repayment.getDueDate().toString(),
                        repayment.getPaymentDate() != null ? repayment.getPaymentDate().toString() : "N/A"
                ),
                new EmiPaymentResponseDTO.LoanDetails(
                        loan.getId(),
                        loan.getRemainingAmount(),
                        loan.getPaidAmount(),
                        loan.getStatus().name()
                )
        );
    }

    @Transactional
    public void createTransaction(Loan loan, Repayment repayment, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID().toString());
        transaction.setLoan(loan);
        transaction.setUser(loan.getUser());
        transaction.setRepayment(repayment);
        transaction.setAmount(amount);
        self.saveTransaction(transaction);
    }

    public Transaction saveTransaction(Transaction transaction){
        return transactionRepository.save(transaction);
    }

}
