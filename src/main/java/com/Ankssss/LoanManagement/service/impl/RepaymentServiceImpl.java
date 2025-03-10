package com.Ankssss.LoanManagement.service.impl;

import com.Ankssss.LoanManagement.constants.RepaymentStatus;
import com.Ankssss.LoanManagement.entity.Loan;
import com.Ankssss.LoanManagement.entity.Repayment;
import com.Ankssss.LoanManagement.repository.RepaymentRepository;
import com.Ankssss.LoanManagement.service.RepaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class RepaymentServiceImpl implements RepaymentService {

    @Autowired
    private RepaymentRepository repaymentRepository;

    @Override
    public void createRepaymentSchedule(Loan loan) {
        List<Repayment> emis = new ArrayList<>();
        BigDecimal emiAmount = loan.getEmiAmount();
        Date startDate = new Date();
        for(int i = 1; i <= loan.getTenure(); i++){
            Repayment repayment = new Repayment();
            repayment.setId(UUID.randomUUID().toString());
            repayment.setLoan(loan);
            repayment.setAmount(emiAmount);
            repayment.setDueDate(calculateDueDate(startDate, i));
            repayment.setStatus(RepaymentStatus.PENDING);
            emis.add(repayment);
        }
        repaymentRepository.saveAll(emis);
    }

    @Override
    public Repayment findById(String repaymentId) {
        return repaymentRepository.findById(repaymentId).orElse(null);
    }

    @Override
    //@CacheEvict(value = "nextRepayment", key = "#repayment.loanId")
    public Repayment updateRepayment(Repayment repayment) {
        return repaymentRepository.save(repayment);
    }

    @Override
    public List<Repayment> findByLoanIdAndStatus(String loanId, String status) {
        List<Repayment> repayments;
        if(status!=null && !status.isEmpty()){
            repayments = repaymentRepository.findByLoanIdAndStatus(loanId, RepaymentStatus.valueOf(status.toUpperCase()));
        }else{
            repayments = repaymentRepository.findByLoanId(loanId);
        }
        if(repayments == null){
            throw new IllegalArgumentException("Invalid loanId or status");
        }
        return repayments;
    }

    @Override
    //@Cacheable(value = "nextRepayment", key = "#loanId")
    public Repayment getNextRepayment(String loanId) {
        return repaymentRepository.findFirstByLoanIdAndStatusOrderByDueDateAsc(
                loanId,
                RepaymentStatus.PENDING
        );
    }

    @Override
    public List<Repayment> getRepaymentsByuserIdAndRole(String userId, String loanId) {
        List<Repayment> repayments = repaymentRepository.findByUserAndLoan(userId, loanId);
        if(repayments == null){
            throw new IllegalArgumentException("No repayments for given userId and loanId");
        }
        return repayments;
    }

    private Date calculateDueDate(Date startDate, int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.MONTH, i);
        return calendar.getTime();
    }

}
